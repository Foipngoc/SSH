package com.module.appversioncheck.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.service.BaseService;
import com.common.utils.DateTimeUtil;
import com.common.utils.FileMd5;
import com.common.utils.barcode.QRCodeUtil;
import com.module.appversioncheck.dao.AppDownloadInfoDao;
import com.module.appversioncheck.dao.AppInfoDao;
import com.module.appversioncheck.dao.AppVersionInfoDao;
import com.module.appversioncheck.model.AppDownloadInfo;
import com.module.appversioncheck.model.AppInfo;
import com.module.appversioncheck.model.AppVersionInfo;
import com.module.appversioncheck.service.AppVersionCheckService;

@Service("appVersionCheckService")
@SuppressWarnings("all")
public class AppVersionCheckServiceImpl extends BaseService implements
		AppVersionCheckService {

	public static String DATA_PATH = "data/appversioncheck";

	@Resource
	private AppDownloadInfoDao appDownloadInfoDao;

	@Resource
	private AppInfoDao appInfoDao;

	@Resource
	private AppVersionInfoDao appVersionInfoDao;

	/**
	 * 获得所有已发布的应用信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> queryApps() {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.findOrderBy(
				new AppInfo(), "createdate", true).getData();

		for (int i = 0; i < appInfos.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("appinfo", appInfos.get(i));
			int newestversionid = appInfos.get(i).getNewestappvid();

			if (newestversionid != -1) {
				AppVersionInfo appVersionInfo = this
						.queryAppVersion(newestversionid);
				if (appVersionInfo != null)
					map.put("newestvcode", appVersionInfo.getVersionname());
				else
					map.put("newestvcode", "");
			} else
				map.put("newestvcode", "");
			ret.add(map);
		}

		return ret;
	}

	@Override
	public Map<String, Object> queryAppDetail(int appid) {
		Map<String, Object> map = new HashMap<>();
		AppInfo appInfo = this.queryApp(appid);
		if (appInfo != null) {
			map.put("appinfo", appInfo);
			int newestversionid = appInfo.getNewestappvid();
			if (newestversionid != -1) {
				AppVersionInfo appVersionInfo = this
						.queryAppVersion(newestversionid);
				if (appVersionInfo != null)
					map.put("newestvcode", appVersionInfo.getVersionname());
				else
					map.put("newestvcode", "");
			} else
				map.put("newestvcode", "");
		}
		return map;
	}

	/**
	 * 获取某一已发布的应用信息
	 * 
	 * @param appid
	 * @return
	 */
	public AppInfo queryApp(int appid) {
		return (AppInfo) this.appInfoDao.findUnique(new AppInfo(), "id", appid);
	}

	/**
	 * 检查应用名是否存在
	 * 
	 * @param appname
	 * @return
	 */
	private boolean checkAppName(String appname) {
		// 检查应用名是否重复
		List<AppInfo> appInfos = (List<AppInfo>) this.appInfoDao.find(
				new AppInfo(), "appname", appname).getData();

		if (appInfos != null && appInfos.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 发布新的应用
	 * 
	 * @param appVersionCheckInfo
	 */
	public BaseResult publishApp(AppInfo appInfo, File logo, String logoname) {
		// 检查应用名是否重复
		if (checkAppName(appInfo.getAppname())) {
			return new BaseResult(1, "应用名已存在");
		}

		appInfo.setCreatedate(DateTimeUtil.getCurrDate());
		appInfo.setNewestappvid(-1);
		this.appInfoDao.save(appInfo);

		if (logo != null) {
			String logodir = getContextPath() + "/" + DATA_PATH + "/logo";
			File logodirfile = new File(logodir);
			if (!logodirfile.exists() && !logodirfile.isDirectory()) {
				logodirfile.mkdirs();
			}
			String logofilepath = logodir + "/" + logoname;
			// 检查目录 上否已经存在同名文件，如果存在改名
			File checklogoFile = new File(logofilepath);
			if (checklogoFile.exists() && checklogoFile.isFile()) {// 同名文件存在
				if (logoname == null || logoname.equals("")) {
					logoname = "/logo_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ ".jpg";
					logofilepath = logodir + "/" + logoname;
				} else {
					logoname = getFileNoExtension(logoname)
							+ "_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ "." + getFileExtension(logoname);
					logofilepath = logodir + "/" + logoname;
				}
			}

			// 保存Logo
			// 写入文件
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(logo);
				File uploadFile = new File(logofilepath);
				out = new FileOutputStream(uploadFile);
				byte[] buffer = new byte[1024 * 1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				return new BaseResult(4, "logo文件保存失败");
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String md5 = FileMd5.getMd5ByFile(logo);
			appInfo.setApplogomd5(md5);
			appInfo.setApplogo(DATA_PATH + "/logo/" + logoname);
			this.appInfoDao.update(appInfo);
		}

		BaseResult result = new BaseResult(0, "发布成功");
		return result;
	}

	/**
	 * 删除已发布的应用
	 * 
	 * @param appid
	 */
	public void delApp(int appid) {
		AppInfo appInfo = this.queryApp(appid);
		if (appInfo != null) {
			List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
			for (int i = 0; i < appVersionInfos.size(); i++) {
				// 删除应用的所有版本
				this.deleteAppVersion(appVersionInfos.get(i).getId());
			}
			// 刪除该应用
			this.appInfoDao.delete(appInfo);
		}
	}

	/**
	 * 更新已发布应用
	 * 
	 * @param appInfo
	 */
	public BaseResult updateApp(int appid, String appname, String appdesc, File logo, String logoname) {

		AppInfo appInfo = this.queryApp(appid);

		if (appInfo == null)
			return new BaseResult(2, "未找到应用");

		if (appname != null && !appname.equals(appInfo.getAppname())) {
			if (checkAppName(appname)) {
				return new BaseResult(1, "应用名已存在");
			}
			appInfo.setAppname(appname);
		}

		if (appdesc != null) {
			appInfo.setAppdesc(appdesc);
		}
		this.appInfoDao.update(appInfo);
		
		if (logo != null) {
			String logodir = getContextPath() + "/" + DATA_PATH + "/logo";
			File logodirfile = new File(logodir);
			if (!logodirfile.exists() && !logodirfile.isDirectory()) {
				logodirfile.mkdirs();
			}
			String logofilepath = logodir + "/" + logoname;
			// 检查目录 上否已经存在同名文件，如果存在改名
			File checklogoFile = new File(logofilepath);
			if (checklogoFile.exists() && checklogoFile.isFile()) {// 同名文件存在
				if (logoname == null || logoname.equals("")) {
					logoname = "/logo_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ ".jpg";
					logofilepath = logodir + "/" + logoname;
				} else {
					logoname = getFileNoExtension(logoname)
							+ "_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ "." + getFileExtension(logoname);
					logofilepath = logodir + "/" +logoname;
				}
			}

			// 保存Logo
			// 写入文件
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(logo);
				File uploadFile = new File(logofilepath);
				out = new FileOutputStream(uploadFile);
				byte[] buffer = new byte[1024 * 1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				return new BaseResult(4, "logo文件保存失败");
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			String md5 = FileMd5.getMd5ByFile(logo);
			appInfo.setApplogomd5(md5);
			appInfo.setApplogo(DATA_PATH + "/logo/" + logoname);
			this.appInfoDao.update(appInfo);
		}
		
		
		BaseResult result = new BaseResult(0, "更新成功");
		return result;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	/**
	 * 发布应用新版本
	 * 
	 * @param appid
	 * @param appVersionInfo
	 * @return
	 */
	public BaseResult publishAppVersion(int appid, boolean autogenvcode,
			int versioncode, String versionname, String updatelog,
			int updatetype, boolean autoset, String downloadpath,
			int autoinstall, File file, String filename) {

		if (updatetype != AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL
				&& updatetype != AppVersionInfo.UPDATE_TYPE_POP_AUTO
				&& updatetype != AppVersionInfo.UPDATE_TYPE_POP_FORCE)
			return new BaseResult(-1, "参数错误");

		AppInfo appInfo = this.queryApp(appid);

		if (appInfo == null)
			return new BaseResult(3, "未找到应用");

		int maxvcode = this.appVersionInfoDao.getMaxVersionCode(appid);

		// 自动生成版本号
		if (autogenvcode) {
			versioncode = maxvcode + 1;
		} else {
			// 版本号必须比上一次发布的版本号增大
			if (versioncode <= maxvcode)
				return new BaseResult(1, "版本号必须大于当前发布版本中最大版本号：" + maxvcode);
		}
		if (file == null) {
			return new BaseResult(2, "APP上传失败");
		}

		File DATA_PATH_FOLDER = new File(getContextPath() + "/" + DATA_PATH +"/res");
		if (!DATA_PATH_FOLDER.exists() && !DATA_PATH_FOLDER.isDirectory()) {
			DATA_PATH_FOLDER.mkdirs();
		}

		String md5 = FileMd5.getMd5ByFile(file);
		if (md5 == null || md5.equals("")) {
			return new BaseResult(5, "MD5校验失败");
		}

		// 获得res目录 ，如果不存在，创建
		String resfolder = DATA_PATH + "/res/" + appid;
		File folder = new File(getContextPath() + "/" + resfolder);
		if (!folder.exists() && !folder.isDirectory()) {
			folder.mkdirs();
		}

		// 获得res文件路径，如果没有filename，默认为app.dl,如果文件已存在，则删除之
		String respath = "";
		if (filename == null || filename.equals(""))
			respath = resfolder + "/app.dl";
		else
			respath = resfolder + "/" + filename;

		// 检查目录 上否已经存在同名文件，如果存在改名
		File checkResFile = new File(getContextPath() + "/" + respath);
		if (checkResFile.exists() && checkResFile.isFile()) {// 同名文件存在
			if (filename == null || filename.equals(""))
				respath = resfolder + "/app_"
						+ DateTimeUtil.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
						+ ".dl";
			else
				respath = resfolder + "/" + getFileNoExtension(filename) + "_"
						+ DateTimeUtil.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
						+ "." + getFileExtension(filename);
		}

		// 写入文件
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			File uploadFile = new File(getContextPath() + "/" + respath);
			out = new FileOutputStream(uploadFile);
			byte[] buffer = new byte[1024 * 1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
		} catch (Exception e) {
			return new BaseResult(4, "文件保存失败");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		AppVersionInfo appVersionInfo = new AppVersionInfo();

		appVersionInfo.setAppid(appid);
		appVersionInfo.setRespath(respath);
		appVersionInfo.setResmd5(md5);
		appVersionInfo.setUpdatelog(updatelog);
		appVersionInfo.setUpdatetype(updatetype);
		appVersionInfo.setVersioncode(versioncode);
		appVersionInfo.setVersionname(versionname);
		appVersionInfo.setUpdatedate(DateTimeUtil.getCurrDate());
		appVersionInfo.setDownloadpath(downloadpath);
		appVersionInfo.setAutoinstall(autoinstall);
		this.appVersionInfoDao.save(appVersionInfo);

		if (autoset) {
			// 更新应用最新版为此次发布 AppInfo appInfo = this.queryApp(appid);
			appInfo.setNewestappvid(appVersionInfo.getId());
			this.appInfoDao.update(appInfo);
		}

		return new BaseResult(0, "发布成功");

	}

	/**
	 * 设置APP最新的版本为哪个
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public BaseResult setNewestAppVersion(int appid, int appvid) {
		AppInfo appInfo = this.queryApp(appid);
		if (appInfo == null)
			return new BaseResult(1, "应用不存在");

		AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
		if (appVersionInfo.getAppid() != appid)
			return new BaseResult(2, "应用不存在该版本");
		appInfo.setNewestappvid(appvid);

		this.appInfoDao.update(appInfo);

		return new BaseResult(0, "设置成功");
	}

	private String getFileExtension(String fileName) {
		if (fileName != null && fileName.lastIndexOf(".") != -1
				&& fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	private String getFileNoExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 更新应用已发布版本信息
	 * 
	 * @param appVersionInfo
	 */
	public BaseResult updateAppVersion(int appvid, String versionname,
			String updatelog, int updatetype, String downloadpath,
			int autoinstall, File file, String filename) {
		AppVersionInfo appVersionInfodb = this.queryAppVersion(appvid);
		if (appVersionInfodb == null)
			return new BaseResult(1, "版本不存在");

		if (versionname != null && !versionname.equals(""))
			appVersionInfodb.setVersionname(versionname);

		if (updatelog != null && !updatelog.equals(""))
			appVersionInfodb.setUpdatelog(updatelog);

		if (updatetype == AppVersionInfo.UPDATE_TYPE_MANUAL_MANUAL
				|| updatetype == AppVersionInfo.UPDATE_TYPE_POP_AUTO
				|| updatetype == AppVersionInfo.UPDATE_TYPE_POP_FORCE)
			appVersionInfodb.setUpdatetype(updatetype);

		if (downloadpath != null && !downloadpath.equals(""))
			appVersionInfodb.setDownloadpath(downloadpath);

		if (autoinstall != -1
				&& (autoinstall == AppVersionInfo.AUTOINSTALL_YES || autoinstall == AppVersionInfo.AUTOINSTALL_NO))
			appVersionInfodb.setAutoinstall(autoinstall);

		if (file != null) {
			File DATA_PATH_FOLDER = new File(getContextPath() + "/" + DATA_PATH +"/res");
			if (!DATA_PATH_FOLDER.exists() && !DATA_PATH_FOLDER.isDirectory()) {
				DATA_PATH_FOLDER.mkdirs();
			}

			String md5 = FileMd5.getMd5ByFile(file);
			if (md5 == null || md5.equals("")) {
				return new BaseResult(5, "MD5校验失败");
			}

			// 获得res目录 ，如果不存在，创建
			String resfolder = DATA_PATH + "/res/" + appVersionInfodb.getAppid();
			File folder = new File(getContextPath() + "/" + resfolder);
			if (!folder.exists() && !folder.isDirectory()) {
				folder.mkdirs();
			}

			// 获得res文件路径，如果没有filename，默认为app.dl,如果文件已存在，则删除之
			String respath = "";
			if (filename == null || filename.equals(""))
				respath = resfolder + "/app.dl";
			else
				respath = resfolder + "/" + filename;

			// 删除原资源文件
			if (appVersionInfodb.getRespath() != null
					&& appVersionInfodb.getRespath().contains(DATA_PATH))
				FileUtils.deleteQuietly(new File(getContextPath() + "/"
						+ appVersionInfodb.getRespath()));

			File checkResFile = new File(getContextPath() + "/" + respath);
			if (checkResFile.exists() && checkResFile.isFile()) {// 目录中存在同名文件
				if (filename == null || filename.equals(""))
					respath = resfolder
							+ "/app_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ ".dl";
				else
					respath = resfolder
							+ "/"
							+ getFileNoExtension(filename)
							+ "_"
							+ DateTimeUtil
									.getCurrTimeFmt("yyyy-MM-dd_HH-mm-ss")
							+ "." + getFileExtension(filename);
			}

			// 写入文件
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(file);
				File uploadFile = new File(getContextPath() + "/" + respath);
				out = new FileOutputStream(uploadFile);
				byte[] buffer = new byte[1024 * 1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				return new BaseResult(4, "文件保存失败");
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			appVersionInfodb.setResmd5(md5);
			appVersionInfodb.setRespath(respath);
		}

		this.appVersionInfoDao.update(appVersionInfodb);
		return new BaseResult(0, "更新成功");
	}

	/**
	 * 删除应用已发布的版本, 注： 如果当前用户已安装了被删除的版本，则用户更新时会提醒强制更新
	 * 
	 * @param appvid
	 */
	public void deleteAppVersion(int appvid) {
		AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
		if (appVersionInfo != null) {
			AppInfo appInfo = this.queryApp(appVersionInfo.getAppid());
			if (appInfo != null) {
				// 如果删除的版本为当前最新的版本，将最新版本往前移一个版本，如果前一个版本没有，则尝试往后一个版本，不成功则设置为-1
				if (appInfo.getNewestappvid() == appVersionInfo.getId()) {
					AppVersionInfo appVersionInfoBefore = queryBeforeAppVersion(
							appVersionInfo.getAppid(), appvid);
					if (appVersionInfoBefore != null)
						appInfo.setNewestappvid(appVersionInfoBefore.getId());
					else {
						AppVersionInfo appVersionInfoAfter = queryAfterAppVersion(
								appVersionInfo.getAppid(), appvid);
						if (appVersionInfoAfter != null)
							appInfo.setNewestappvid(appVersionInfoAfter.getId());
						else
							appInfo.setNewestappvid(-1);
					}
					this.appInfoDao.update(appInfo);
				}
				String respath = appVersionInfo.getRespath();
				if (respath != null && respath.contains(DATA_PATH))
					// 删除资源
					new File(getContextPath() + "/" + respath).delete();
				this.appVersionInfoDao.delete(appVersionInfo);
			}
		}
	}

	/**
	 * 获取某应用发布的所有版本信息
	 * 
	 * @param appid
	 * @return
	 */
	public List<AppVersionInfo> queryAppVersions(int appid) {
		List<AppVersionInfo> list = (List<AppVersionInfo>) this.appVersionInfoDao
				.findOrderBy(new AppVersionInfo(), "appid", appid,
						"updatedate", true).getData();
		return list;
	}

	/**
	 * 获取某应用的某版本信息
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	public AppVersionInfo queryAppVersion(int appvid) {
		AppVersionInfo appVersionInfo = (AppVersionInfo) this.appVersionInfoDao
				.findUnique(new AppVersionInfo(), "id", appvid);
		return appVersionInfo;
	}

	/**
	 * 通过版本号获取某应用的版本信息
	 * 
	 * @param appid
	 * @param vcode
	 * @return
	 */
	public AppVersionInfo queryAppVersion(int appid, int vcode) {
		AppVersionInfo appVersionInfo = this.appVersionInfoDao
				.queryAppVersionInfo(appid, vcode);
		return appVersionInfo;
	}

	/**
	 * 查询appvid的前一版
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	private AppVersionInfo queryBeforeAppVersion(int appid, int appvid) {
		AppVersionInfo appVersionInfo = null;
		List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
		int i = 0;
		for (; i < appVersionInfos.size(); i++) {
			if (appVersionInfos.get(i).getId() == appvid)
				break;
		}

		if (i > 0)
			return appVersionInfos.get(i - 1);
		else
			return null;
	}

	/**
	 * 查询appvid的后一版
	 * 
	 * @param appid
	 * @param appvid
	 * @return
	 */
	private AppVersionInfo queryAfterAppVersion(int appid, int appvid) {
		AppVersionInfo appVersionInfo = null;
		List<AppVersionInfo> appVersionInfos = this.queryAppVersions(appid);
		int i = 0;
		for (; i < appVersionInfos.size(); i++) {
			if (appVersionInfos.get(i).getId() == appvid)
				break;
		}

		if (i < appVersionInfos.size() - 1)
			return appVersionInfos.get(i + 1);
		else
			return null;
	}

	/**
	 * 检查新版本信息
	 * 
	 * @param appid
	 * @param oldversioncode
	 * @return
	 */
	public BaseResult checkNewestAppVersion(int appid, int oldversioncode) {
		AppInfo appInfo = this.queryApp(appid);

		if (appInfo == null) {
			return new BaseResult(0, "不存在该应用");
		}

		// 获得最新版本APP
		AppVersionInfo newestAppVersionInfo = null;

		if (appInfo.getNewestappvid() != -1)
			try {
				newestAppVersionInfo = this.queryAppVersion(
						appInfo.getNewestappvid()).cloneObject();// clone对象，避免hibernate对持久化对象自动保存
			} catch (Exception e) {
				newestAppVersionInfo = null;
			}

		// 如果没有最新版本，则提醒已为最新版本，无需更新
		if (newestAppVersionInfo == null) {
			return new BaseResult(1, "已为最新版本");
		}

		// 如果系统没有oldversioncode这个版本，则强制更新到最新版
		AppVersionInfo oldAppVersionInfo = this.queryAppVersion(appid,
				oldversioncode);
		if (oldAppVersionInfo == null) {
			newestAppVersionInfo
					.setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
			BaseResult result = new BaseResult(2, "有新版本可以升级");
			result.addToMap("appversioninfo", newestAppVersionInfo);
			result.addToMap("appinfo", appInfo);
			return result;
		}

		// 如果原版本大于或等于当前最新版本
		if (oldAppVersionInfo.getVersioncode() >= newestAppVersionInfo
				.getVersioncode()) {
			return new BaseResult(1, "已为最新版本");
		}

		/**
		 * 寻找在最新版本与old版本之间的所有版本
		 */
		List<AppVersionInfo> appVersionInfos = this.appVersionInfoDao
				.queryAppVersionInfos(appid,
						newestAppVersionInfo.getVersioncode(),
						oldAppVersionInfo.getVersioncode());

		/**
		 * 1. 如果从oldversioncode到最新版本，存在需要强制更新的版本，则返回的版本信息中为必须强制更新 2. 更新日志需要叠加
		 */
		String updatelog = newestAppVersionInfo.getVersionname() + "\r\n";
		for (int i = 0; i < appVersionInfos.size(); i++) {
			AppVersionInfo appVersionInfo = appVersionInfos.get(i);

			updatelog += appVersionInfo.getUpdatelog() + "\r\n";

			if (appVersionInfo.getUpdatetype() == AppVersionInfo.UPDATE_TYPE_POP_FORCE) {
				newestAppVersionInfo
						.setUpdatetype(AppVersionInfo.UPDATE_TYPE_POP_FORCE);
				break;
			}
		}
		newestAppVersionInfo.setUpdatelog(updatelog.toString());

		BaseResult result = new BaseResult(2, "有新版本可以升级");

		result.addToMap("appversioninfo", newestAppVersionInfo);
		result.addToMap("appinfo", appInfo);
		return result;
	}

	/**
	 * 下载最新版本
	 * 
	 * @param appid
	 * @return
	 */
	public String downloadNewestAppVersionRes(int appid, int oldvesioncode,
			String clientinfo) {

		BaseResult checkResult = this.checkNewestAppVersion(appid,
				oldvesioncode);

		// 先检查下是否允许更新
		if (checkResult.getResultcode() == 2) {
			// 获得最新版本APP
			AppInfo appInfo = this.queryApp(appid);
			AppVersionInfo oldAppVersionInfo = this.queryAppVersion(appid,
					oldvesioncode);
			AppVersionInfo newestAppVersionInfo = this.queryAppVersion(appInfo
					.getNewestappvid());

			if (newestAppVersionInfo.getRespath() == null
					|| newestAppVersionInfo.getRespath().equals("")) {
				return null;
			}

			String filepath = newestAppVersionInfo.getRespath();
			String filemd5 = FileMd5.getMd5ByFile(getContextPath() + "/"
					+ filepath);
			if (!filemd5.toLowerCase().equals(
					newestAppVersionInfo.getResmd5().toLowerCase()))
				return null;
			/**
			 * 添加下载信息
			 */
			AppDownloadInfo appDownloadInfo = new AppDownloadInfo();
			appDownloadInfo.setAppid(appid);
			appDownloadInfo.setAppvid(newestAppVersionInfo.getId());
			if (oldAppVersionInfo != null)
				appDownloadInfo.setOldappvid(oldAppVersionInfo.getId());
			else
				appDownloadInfo.setOldappvid(-1);
			appDownloadInfo.setUpdatedate(new Date());
			if (clientinfo != null)
				appDownloadInfo.setClientinfo(clientinfo);
			else
				appDownloadInfo.setClientinfo("");
			this.appDownloadInfoDao.save(appDownloadInfo);

			return filepath;
		} else {
			return null;
		}
	}

	/**
	 * 下载某版本资源
	 * 
	 * @param appid
	 * @return
	 */
	public String downloadAppVersionRes(int appvid) {
		AppVersionInfo appVersionInfo = this.queryAppVersion(appvid);
		if (appVersionInfo != null) {
			String filepath = appVersionInfo.getRespath();
			String filemd5 = FileMd5.getMd5ByFile(getContextPath() + "/"
					+ filepath);
			if (!filemd5.toLowerCase().equals(
					appVersionInfo.getResmd5().toLowerCase()))
				return null;
			return filepath;
		}
		return null;
	}

	@Override
	public BaseResult genBarCode(int appid, String url) {
		if (url == null)
			return new BaseResultFailed("URL不能为空");
		String logopath = null;
		
		AppInfo appInfo = this.queryApp(appid);

		if (appInfo == null) {
			return new BaseResultFailed("不存在该应用");
		}
		logopath = getContextPath()+"/"+appInfo.getApplogo();
		
		if (!checkMd5(logopath, appInfo.getApplogomd5())) {
			logopath = null;
		}
		
		if (logopath == null) {
			try {
				QRCodeUtil.encode(url, getContextPath() + "/" + DATA_PATH
						+ "/barcode"+appid+".jpg",true);
			} catch (Exception e) {
				return new BaseResultFailed("生成二维码失败");
			}
		}else {
			try {
				QRCodeUtil.encode(url, logopath, getContextPath() + "/" + DATA_PATH
						+ "/barcode"+appid+".jpg",true);
			} catch (Exception e) {
				return new BaseResultFailed("生成二维码失败");
			}
		}
		return new BaseResultOK(DATA_PATH + "/barcode"+appid+".jpg");
	}
	
	private boolean checkMd5(String filepath, String md5) {
		if (filepath == null || md5 == null)
			return false;
		String filemd5 = FileMd5.getMd5ByFile(filepath);
		if (filemd5 != null
				&& filemd5.toLowerCase(Locale.getDefault()).equals(
						md5.toLowerCase(Locale.getDefault())))
			return true;
		return false;
	}
}

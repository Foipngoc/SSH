package com.updown_load.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.utils.FileMd5;
import com.common.utils.FileUtils;

@Controller
public class Upload extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7479732448969184794L;
	private BaseResult result;

	private List<File> upFiles;
	private List<String> upFilesFileName;
	private List<String> upFilesContentType;

	public void setUpFiles(List<File> upFiles) {
		this.upFiles = upFiles;
	}

	public void setUpFilesContentType(List<String> upFilesContentType) {
		this.upFilesContentType = upFilesContentType;
	}

	public void setUpFilesFileName(List<String> upFilesFileName) {
		this.upFilesFileName = upFilesFileName;
	}

	public BaseResult getResult() {
		return result;
	}

	/**
	 * 下载某版本版本资源
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String upload() {
		List<BaseResult> saveresult = new ArrayList<>();
		if (upFiles != null) {
			for (int i = 0; i < upFiles.size(); i++) {
				BaseResult ret = savefile(upFiles.get(i), getContextPath(),
						upFilesFileName.get(i));
				saveresult.add(ret);
			}
		}
		result = new BaseResultOK(saveresult);
		return SUCCESS;
	}

	public BaseResult savefile(File file, String savedir, String filename) {
		String uploadmd5 = FileMd5.getMd5ByFile(file);

		FileUtils.mkdir(savedir);
		// 相同md5且同名的文件已存在，不需要保存
		if (FileUtils.ifFileExist(savedir + "/" + filename, uploadmd5)) {
			return new BaseResultOK();
		} else {
			// 如果文件同名
			if (FileUtils.ifFileExist(savedir + "/" + filename)) {
				filename = FileUtils.renameFileName(filename,
						"" + new Date().getTime());
			}
			// 写入文件
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(file);
				File uploadFile = new File(savedir + "/" + filename);
				out = new FileOutputStream(uploadFile);
				byte[] buffer = new byte[1024 * 1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				return new BaseResultOK();
			} catch (Exception e) {
				return new BaseResultFailed("文件保存失败");
			} finally {
				try {
					in.close();
				} catch (IOException e) {
				}

				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
}

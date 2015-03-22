package com.module.fileUpDownLoad.service.impl;

import java.io.File;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.service.BaseService;
import com.module.fileUpDownLoad.service.FileUpDownloadService;

/**
 * 为了防止用户使用该模块后，前台非法用户可以随意下载或者上传服务器上的文件，此类不实现相应的功能。
 * 
 * 既下载文件时，返回空流，上传文件时，简单丢充流.
 * 
 * @author DJ
 * 
 */
@Service("fileUpDownloadService")
public class FileUpDownloadServiceImpl extends BaseService implements
		FileUpDownloadService {

	@Override
	public BaseResult upLoadFile(File file, String filename) {
		/**
		 * 将file对象保存入磁盘
		 */
		return new BaseResult(0, "文件已上传");
	}

	@Override
	public BaseResult downLoadFile(String filepath) {
		BaseResult result = new BaseResult();
		/**
		 * 将filepath的文件写入InputStream。
		 */
		result.addToMap("inputstream", null);
		result.addToMap("filename", "");
		return result;
	}

}

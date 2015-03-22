package com.module.fileUpDownLoad.service;

import java.io.File;

import com.common.action.BaseResult;

public interface FileUpDownloadService {
	/**
	 * 上传文件
	 * @param file
	 * @param filename
	 * @return
	 */
	public BaseResult upLoadFile(File file,String filename);
	
	/**
	 * 下载文件
	 * @param filepath
	 * @return
	 */
	public BaseResult downLoadFile(String filepath);
}

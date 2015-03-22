package com.module.fileUpDownLoad.action;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.module.fileUpDownLoad.service.FileUpDownloadService;

@Namespace("/module/FileUpDownLoadAction")
public class FileUpDownLoadAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9071791478123092420L;

	//文件上传
	private List<File> file;
	private List<String> fileFileName;
	@SuppressWarnings("unused")
	private List<String> fileContentType;

	//文件下载
	private String filepath;
	private InputStream inputStream;
	private String filename;
	
	private List<BaseResult> results;
	private BaseResult result;

	@Resource
	private FileUpDownloadService fileUpDownloadService;

	/**
	 * 上传接口
	 * 
	 * @return
	 */
	@Action(value = "upload", results = { @Result(name = "success", type = "json") })
	public String upload() {
		results = new ArrayList<>();
		if (file != null) {
			for (int i = 0; i < file.size(); i++) {
				BaseResult ret = this.fileUpDownloadService.upLoadFile(
						file.get(i), fileFileName.get(i));
				results.add(ret);
			}
		}
		return SUCCESS;
	}

	/**
	 * 下载接口,下载成功，直接返回文件流，下载失败，返回JSON数据。
	 * 
	 * @return
	 */
	@Action(value = "download", results = {
			@Result(name = "stream", type = "stream", params = {
					"contentDisposition", "attachment;filename='${filename}'",
					"bufferSize", "4096" }),
			@Result(name = "json", type = "json") })
	public String download() {
		BaseResult result2 = this.fileUpDownloadService.downLoadFile(filepath);
		if (result2.getResultcode() == 2) {
			inputStream = (InputStream) result2.getFromMap("inputstream");
			filename = (String) result2.getFromMap("filename");
			return "stream";
		} else {
			result = result2;
			return "json";
		}
	}

	public List<BaseResult> getResults() {
		return results;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFilename() {
		return filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}

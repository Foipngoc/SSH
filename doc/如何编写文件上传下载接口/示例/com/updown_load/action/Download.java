package com.updown_load.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;

@Controller
public class Download extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7479732448969184794L;

	private long dlFilelength; // 下载文件长度
	private InputStream dlFile; // 下载文件流
	private String dlFileName; // 下载文件名

	public InputStream getDlFile() {
		return dlFile;
	}

	public long getDlFilelength() {
		return dlFilelength;
	}

	public String getDlFileName() {
		return dlFileName;
	}

	/**
	 * 下载某版本版本资源
	 * 
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public String download() throws FileNotFoundException,
			UnsupportedEncodingException {
		String filename="测试中文下载.pdf"; //tomcat项目根目录下的一个文件
		String filePath = getContextPath() + "/" + filename; //绝对路径
		dlFile = new FileInputStream(filePath);
		dlFileName = new File(filePath).getName();
		dlFilelength = new File(filePath).length();
		dlFileName = filenameEncode(dlFileName); //文件名编码,安卓端需要用urldecode进行解码
		return "file";
	}
}

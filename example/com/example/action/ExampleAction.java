package com.example.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.example.service.ExampleService;

@Controller //声明Bean，控制层
public class ExampleAction extends BaseAction {
	private static final long serialVersionUID = 7170012130807429944L;
	
	@Resource //注入Bean
	private ExampleService exampleService;
	private String string;
	public String deal() {
		string = "example";
		exampleService.addExample();
		return SUCCESS;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
}

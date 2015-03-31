package com.example.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.example.model.Example;
import com.example.service.ExampleService;

@Controller
@ParentPackage("cement-interceptor")
@Namespace("/example")
public class ExampleAction extends BaseAction {

	@Resource(name = "exampleservice")
	private ExampleService exampleService;
	private Example example;
	private Example example2;
	private int page;
	private int rows;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7764785343371246557L;
	private String input;
	private BaseResult result;

	@Action(value = "doaction", results = { @Result(name = "json", type = "json")})
	public String doAction() {
		if (example2 != null) {
			String name = example2.getName();
			int age = example2.getAge();
		}
		if (example != null) {
			String name = example.getName();
			int age = example.getAge();
		}
		if (input != null && input != "") {
			this.baseResult = new BaseResult(1, "example");
			this.result = new BaseResult(1, "examples");
			this.result.setObj(this.exampleService.getExamples(page, rows));
		}
		return "json";
	}

	public void setInput(String input) {
		this.input = input;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void setExample(Example example) {
		this.example = example;
	}
	
	public void setExample2(Example example2) {
		this.example2 = example2;
	}
	public Example getExample() {
		return example;
	}
	
	public Example getExample2() {
		return example2;
	}
}

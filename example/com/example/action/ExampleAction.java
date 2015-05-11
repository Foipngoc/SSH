package com.example.action;

import javax.annotation.Resource;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.example.model.Student;
import com.example.service.ExampleService;

/**
 * Action层接口类
 * 
 * 该类需要继承BaseAction
 * 
 * @author DJ
 * 
 */
@SuppressWarnings("all")
// 忽略该类中的所有警告
public class ExampleAction extends BaseAction {

	/**
	 * 使用注解从spring托管库中注入exampleservice， 也就是ExampleServiceImpl类的实例
	 */
	@Resource(name = "exampleservice")
	private ExampleService exampleService;
	private Student example; // 对象传值，需要同时编写get/set方法
	private Student example2;
	private int page; // 属性传值， 传入值添加set方法，传出值添加get方法
	private int rows;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7764785343371246557L;
	private String input;
	private BaseResult result;

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
			this.result = new BaseResult(1, "examples",
					this.exampleService.getExamples(page, rows));
		}
		return "json";
	}

	public String doAction2() {
		throw new RuntimeException();
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

	public void setExample(Student example) {
		this.example = example;
	}

	public void setExample2(Student example2) {
		this.example2 = example2;
	}

	public Student getExample() {
		return example;
	}

	public Student getExample2() {
		return example2;
	}
}

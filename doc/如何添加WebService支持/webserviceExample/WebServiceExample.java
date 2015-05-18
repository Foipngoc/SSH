package com.webserviceExample;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://webservice.com", name = "WebServiceExample")
public class WebServiceExample {
	@WebMethod(operationName = "example", action = "urn:example")
	@WebResult(name="ret")
	public String dowebservice(
			@WebParam(name = "age", targetNamespace = "example") int age,
			@WebParam(name = "name", targetNamespace = "example") String name) {
		System.out.println(name + ":" + age);
		
		return name+age;
	}
}

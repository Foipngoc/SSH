<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>版本</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">	
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>module/appVersionCheck/modifyapp.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

  </head>
  
  <body>
  <input type="hidden" value="<%=basePath%>" id="basePath" />
  <input type="hidden" value="<%=request.getParameter("appId")%>" id="appId">
  	<button onclick="sendVersion()">发布新版本</button>
  	<button onclick="goToBack()">返回</button>
		   <table class="listTable" id="" cellpadding="0" cellspacing="0">
						<col width="10%"/><col width="20%"/><col width="70%"/>
							<tr>
								<th>编号</th>
								<th>版本</th>
								<th>操作</th>
							</tr>
							<!-- <tr>
								<td>1</td>
								<td>XX版本</td>
								<td><a onclick="modifyVersion()">修改版本</a>&nbsp;&nbsp;<a onclick="deleteTheVersion()">删除版本</a></td>
							</tr> -->
						</table> 
			<!-- 发布新版本 -->
			<div id="addAppVersionDiv" style="display:none;height:100px;width:200px;border:solid 1px black;">
		   	<div>
		   		<table cellpadding="0" cellspacing="0">
		   		<col width="20%"><col width="80%">
		   			<tr>
		   				<td>版本名</td>
		   				<td><input type="text" id="appname" /></td>
		   			</tr>
		   			<tr>
		   				<td>资源路径</td>
		   				<td><input type="text" id="appname" /></td>
		   			</tr>
		   			<tr>
		   				<td><button onclick="addApp()">确定</button></td>
		   				<td><button onclick="canceladd()">取消</button></td>
		   			</tr>
		   		</table>
		   	</div>
		   </div>
		   
  </body>
</html>

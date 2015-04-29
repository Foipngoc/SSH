<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title id="apptitle">发布应用版本</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/table.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/style.css">

<script src="js/common/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/addappversioninfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body>
	<div align="left" style="width: 80%">
	<form id="form" enctype="multipart/form-data" action="module/appversioncheck/publishAppVersion" method="post">
	<input type="hidden" value="<%=request.getParameter("appid")%>" name="appid" id="appid"/>
	<h2>应用名&nbsp;&nbsp;<input type="text" name="appname" id="appname" readonly="readonly" style="border: 0px; font-weight: bold;"></h2> <br>
	<h2>版本号&nbsp;&nbsp;<input type="text" name="versioncode" id="versioncode"><input type="hidden" value="0" name="autogenvcode"></h2> <br>
	<h2>版本名&nbsp;&nbsp;<input type="text" name="versionname" id="versionname"> </h2><br>
	<h2>更新日志&nbsp;<textarea rows="6" cols="40" name="updatelog" id="updatelog"></textarea></h2><br>
	<h2>更新方式&nbsp;<select name="updatetype" id="updatetype"> <option value="0">强制更新</option><option value="1">用户选择</option><option value="2">不弹出更新</option></select> </h2><br>
	<h2>选择APP&nbsp;&nbsp;<input type="file" name="file" id="file"></h2><br>
	<h2>自动设置&nbsp;<select name="autoset" id="autoset"><option value="1">自动设置</option><option value="0">不自动设置</option></select></h2><br>
	<h2>下载路径&nbsp;<input type="text" name="downloadpath" id="downloadpath"> </h2><br>
	<h2>自动安装&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="autoinstall" id="autoinstall"><option value="1">自动安装</option><option value="0">不自动安装</option></select> </h2><br>
	<input type="button" value="发布新版本" onclick="publicAppversioninfo()">
	</form>
	</div>
</body>
</html>

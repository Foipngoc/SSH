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

<title id="apptitle">应用版本</title>

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
	src="<%=basePath%>module/appversioncheck/js/appversioninfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body>
	<input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
	<div align="center" style="width: 60%">
	<h1 id="title">当前应用所有版本</h1>
	<table class="listTable" id="" cellpadding="0" cellspacing="0">
		<col width="5%" />
		<col width="8%" />
		<col width="10%" />
		<col width="20%" />
		<col width="10%" />
		<col width="30%" />
		<col width="20%" />
		<tr>
			<th>编号</th>
			<th>版本号</th>
			<th>版本名</th>
			<th>更新时间</th>
			<th>更新方式</th>
			<th>APP名</th>
			<th>操作</th>
		</tr>
	</table>
	</div>
	<input type="hidden" value="<%=request.getParameter("appid")%>" id="appid"/>
	<input type="button" value="发布新版本" onclick="addAppVersion()">
</body>
</html>

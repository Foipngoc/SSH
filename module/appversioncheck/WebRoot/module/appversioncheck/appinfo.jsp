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

<title>所有应用</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/table.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/common/style.css">

<script src="js/common/jquery-1.10.2.min.js"></script>
<script src="js/common/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>module/appversioncheck/js/appinfo.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body style="">
	<input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
	<div align="center" style="width: 80%">
	<h1>当前系统所有应用</h1>
	<table class="listTable" id="" cellpadding="0" cellspacing="0">
		<col width="10%" />
		<col width="10%" />
		<col width="10%" />
		<col width="20%" />
		<col width="20%" />
		<col width="10%" />
		<col width="20%" />
		<tr>
			<th>应用ID</th>
			<th>应用LOGO</th>
			<th>应用名</th>
			<th>应用描述</th>
			<th>创建时间</th>
			<th>最新版版本</th>
			<th>操作</th>
		</tr>
		
	</table>
	</div>
	<input type="button" value="发布新应用" onclick="window.location.href='<%=basePath%>/module/appversioncheck/addappinfo.jsp'">
	
	<div id="win" style="display:none; POSITION:absolute; left:50%; top:50%; width:600px; height:400px; margin-left:-300px; margin-top:-200px; border:0px solid #888; background: #eeeeee; text-align:left">
		<textarea id="newdlurl" rows="6" cols="70"></textarea><input type="button" value="取消" onclick="hideUrlBar()">
		<img id="newdlurlbarcode" src="">
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>应用版本</title>

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
	src="<%=basePath%>module/appversioncheck/js/publishappversionresult.js"></script>
<script type="text/javascript" src="js/common/paging.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

</head>

<body>
	<input type="hidden" value="<%=request.getParameter("appid")%>" id="appid"/>
	<input type="hidden" value="<%=request.getParameter("resultcode")%>" id="resultcode"/>
</body>
</html>

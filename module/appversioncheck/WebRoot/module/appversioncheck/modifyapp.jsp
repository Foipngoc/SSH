<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title id="apptitle">修改应用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">	
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>module/appversioncheck/js/modifyapp.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

  </head>
  
  <body>
  <input id="basePath" name="basePath" type="hidden" value="<%=basePath%>">
  <input type="hidden" value="<%=request.getParameter("appid")%>" id="appid">
  <input type="hidden" value="" id="newestappvid">
  	<b>应用名&nbsp&nbsp</b><input style="width: 30%" type="text" id="appname"><br><br>
  	<b>应用描述&nbsp</b><textarea style="width: 30%; height:60px" type="text" id="appdesc"></textarea><br><br>
  	<b>最新版本&nbsp</b><input style="width: 25%" type="text" readonly=true id="newestversion"><input type="button" id="selversion" value="选择最新版本" onclick="selversion()"><br><br>
	<input type="button" onclick="modifyappinfo()" value="修改应用">
  
  	<div id=win style="display:none; POSITION:absolute; left:50%; top:50%; width:600px; height:400px; margin-left:-300px; margin-top:-200px; border:1px solid #888; background-color:#edf; text-align:left">
		<table class="listTable" id="listTable" cellpadding="0" cellspacing="0">
		<col width="20%" />
		<col width="20%" />
		<col width="30%" />
		<col width="20%" />
		<col width="20%" />
		<tr>
			<th>选择</th>
			<th>版本号</th>
			<th>版本名</th>
			<th>更新时间</th>
			<th>更新方式</th>
		</tr>
	</table>
	<input type="button" value="确认" onclick="setsel()"><input type="button" value="取消" onclick="document.getElementById('win').style.display='none';">
	</div>
  
  </body>
</html>

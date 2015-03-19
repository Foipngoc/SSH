<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>应用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/table.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/common/style.css">
	
	<script src="js/common/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>module/appVersionCheck/application.js"></script>
	<script type="text/javascript" src="js/common/paging.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/WdatePicker/WdatePicker.js"></script>

  </head>
  
  <body>
  <input type="hidden" value="<%=basePath%>" id="basePath" />
  <button onclick="addapplication()">添加应用</button>
					<table class="listTable" id="" cellpadding="0" cellspacing="0">
						<col width="10%"/><col width="20%"/><col width="70%"/>
							<tr>
								<th>编号</th>
								<th>应用</th>
								<th>操作</th>
							</tr>
							<tr>
								<td>1</td>
								<td>XX应用</td>
								<td><a onclick="modify()">修改</a>&nbsp;&nbsp;<a onclick="deleteTheApp()">删除</a></td>
							</tr>
						</table> 
						<div class="User_S4" id="pageDiv" style="background:#f3f3f3;display:none;">
							<p>
								<span class="firstBtnSpan"></span>
								<span class="prevBtnSpan"></span>
								<span class="pageNoSpan"></span>页
								<span class="nextBtnSpan"></span>
								<span class="lastBtnSpan"></span>
								<span class="gotoPageSpan"></span>
							</p>
						</div>
		    		<div class="dialog" id="DeleteDiv" style="display:none;height:100px;width:102px;background: green;">
		    			<div class="User_Top1">
		    				<label class="Top_left">删&nbsp;&nbsp;除</label>
		    				<label  class="Top_right"
		    					 onclick="closeDialog2()">关闭</label>
		    			</div>
		    			<div class="User_DeleteMiddle">
		    				<div class="Middle_p2">您确定要删除这些举报信息吗？</div>
		    			</div>
		    			<div class="User_Deletebottom">
		    					<div class="bottom_button1"  onclick="closeDialog2()">取消</div>
								<div class="bottom_button2"  onclick="DeleteReport()">确定</div>	
		    			</div>
		    		</div>
		   
		   
  </body>
</html>
function modify(){
	window.location.href=$("#basePath").val()+"module/appVersionCheck/modifyapp.jsp";
}
function deleteTheApp(){
	if(confirm("你确定要删除该应用吗？")){
		return;
	}
}
function addapplication(){
	if(confirm("你确定要添加新版本吗？")){
		return;
	}
}
function showdeleteDiv(){
	$("#DeleteDiv").show();
	
}
function hideDiv(){
	//$(document)
	if($(event.target).attr("id")!="DeleteDiv")
	$("#DeleteDiv").hide();
}
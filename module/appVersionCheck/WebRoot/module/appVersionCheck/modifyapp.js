$(document).ready(function(){
	showAppversionInfo('module/AppVersionCheck/queryAppVersions',1);
});

function showAppversionInfo(actionName,selectedPage){
	$.ajax({
		url:actionName,
		type:'post',
		dataType:'json',
		data:{
			'appInfo.id':$("#appId").val()
		},
		success:function(data){
			var list=data.result.map.appversioninfos;
			appendToTable(list);
		}
	});
}
function appendToTable(list){
	for(var i=0;i<list.length;i++){
		var newTr=$("<tr class='addTr'></tr>");
		newTr.append($("<td>"+(i+1)+"</td>"));
		newTr.append($("<td>"+list[i].versionname+"</td>"));
		newTr.append($("<td><a onclick='modifyVersion("+list[i].id+")'>修改版本</a>&nbsp;&nbsp;<a " +
				"onclick='deleteTheVersion("+list[i].id+")'>删除版本</a></td>"));
		$(".listTable").append(newTr);
	}
}
function deleteTheVersion(id){
	if(confirm("你确定要删除该版本吗？")){
		$.ajax({
			url:'module/AppVersionCheck/deleteAppVersion',
			type:'post',
			dataType:'json',
			data:{
				'appVersionInfo.id':id
			},
			success:function(data){
				window.location.reload();
			}
		});
	}
}
function modifyVersion(){
	if(confirm("你确定要修改该版本吗？")){
		return;
	}
}
function sendVersion(){
	if(confirm("你确定要发布新版本吗？")){
		return;
	}
}
function goToBack(){
	window.history.back();
}
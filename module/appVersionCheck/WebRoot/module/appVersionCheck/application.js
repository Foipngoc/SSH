$(document).ready(function() {
	showAppList();
});

function showAppList() {
	$.ajax({
		url : 'module/AppVersionCheck/queryApps',
		type : 'post',
		dataType : 'json',
		data : {

		},
		success : function(data) {
			var list = data.result.map.appinfos;
			for (var i = 0; i < list.length; i++) {
				var newTr = $("<tr class='addTr'></tr>");
				newTr.append($("<td>" + (i + 1) + "</td>"));
				newTr.append($("<td>" + list[i].appinfo.appname + "</td>"));
				newTr.append($("<td>" + list[i].appinfo.appdesc + "</td>"));
				newTr.append($("<td>" + list[i].appinfo.createdate + "</td>"));
				newTr.append($("<td>" + list[i].newestvcode + "</td>"));
				newTr.append($("<td><a onclick='modifyApp("
						+ list[i].appinfo.id
						+ ")'>修改</a>&nbsp;&nbsp;<a onclick='delApp("
						+ list[i].appinfo.id + ")'>删除</a></td>"));
				$(".listTable").append(newTr);
			}
		}
	});
}

function modifyApp(id) {
	window.location.href = $("#basePath").val()
			+ "module/appVersionCheck/modifyapp.jsp?appId=" + id;
}

function delApp(id) {
	if (confirm("你确定要删除该应用吗？")) {
		$.ajax({
			url : 'module/AppVersionCheck/delApp',
			type : 'post',
			dataType : 'json',
			data : {
				'appid' : id
			},
			success : function(data) {
				window.location.reload();
			}
		});
	}
}
function addapplication() {
	$("#addAppDiv").show();
}
function canceladd() {
	$("#addAppDiv").hide();
}
function addApp() {
	$.ajax({
		url : 'module/AppVersionCheck/publishAppInfos',
		type : 'post',
		dataType : 'json',
		data : {
			'appInfo.appname' : $("#appname").val()
		},
		success : function(data) {
			window.location.reload();
		}
	});
}

function showdeleteDiv() {
	$("#DeleteDiv").show();
}
function hideDiv() {
	// $(document)
	if ($(event.target).attr("id") != "DeleteDiv")
		$("#DeleteDiv").hide();
}
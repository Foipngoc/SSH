$(document).ready(function() {
	showAppList();
});

function showAppList() {
	$.ajax({
		url : 'module/appversioncheck/queryApps',
		type : 'post',
		dataType : 'json',
		data : {

		},
		success : function(data) {
			var list = data.result.map.appinfos;
			for (var i = 0; i < list.length; i++) {
				var newTr = $("<tr class='addTr'></tr>");
				newTr.append($("<td>" + list[i].appinfo.id + "</td>"));
				newTr.append($("<td><a style='color:blue;text-decoration:underline' href='"+$("#basePath").val()+"module/appversioncheck/appversioninfo.jsp?appid="
							+list[i].appinfo.id+"'>" + list[i].appinfo.appname + "</a></td>"));
				newTr.append($("<td>" + list[i].appinfo.appdesc + "</td>"));
				newTr.append($("<td>" + list[i].appinfo.createdate + "</td>"));
				newTr.append($("<td>" + list[i].newestvcode + "</td>"));
				newTr.append($("<td><a style='color:blue;text-decoration:underline' onclick='modifyApp("
						+ list[i].appinfo.id
						+ ")'>修改</a>&nbsp;&nbsp;"+"<a style='color:blue;text-decoration:underline' onclick='delApp("
						+ list[i].appinfo.id + ")'>删除</a></td>"));
				$(".listTable").append(newTr);
			}
		}
	});
}

function modifyApp(appid) {
	window.location.href = $("#basePath").val()+"module/appversioncheck/modifyapp.jsp?appid="+ appid;
}

function delApp(id) {
	if (confirm("你确定要删除该应用吗？")) {
		$.ajax({
			url : 'module/appversioncheck/delApp',
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
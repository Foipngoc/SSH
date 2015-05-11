$(document).ready(function() {
	showAppList();
});

function showAppList() {
	$
			.ajax({
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
						newTr.append($("<td><img width='50' height='50' src='"+$("#basePath").val()+"/"+ list[i].appinfo.applogo +"'></img></td>"));
						newTr
								.append($("<td><a style='color:blue;text-decoration:underline' href='"
										+ $("#basePath").val()
										+ "module/appversioncheck/appversioninfo.jsp?appid="
										+ list[i].appinfo.id
										+ "'>"
										+ list[i].appinfo.appname + "</a></td>"));
						newTr.append($("<td>" + list[i].appinfo.appdesc
								+ "</td>"));
						newTr.append($("<td>" + list[i].appinfo.createdate
								+ "</td>"));
						newTr.append($("<td>" + list[i].newestvcode + "</td>"));
						newTr
								.append($("<td><a style='color:blue;text-decoration:underline' onclick='modifyApp("
										+ list[i].appinfo.id
										+ ")'>修改</a>&nbsp;&nbsp;"
										+ "<a style='color:blue;text-decoration:underline' onclick='delApp("
										+ list[i].appinfo.id
										+ ")'>删除</a>&nbsp;&nbsp;"
										+ "<a style='color:red;text-decoration:underline' onclick='showAppDownLink("
										+ list[i].appinfo.id
										+ ")'>下载链接</a>"
										+ "</td>"));
						$(".listTable").append(newTr);
					}
				}
			});
}

function modifyApp(appid) {
	window.location.href = $("#basePath").val()
			+ "module/appversioncheck/modifyapp.jsp?appid=" + appid;
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

function showAppDownLink(id) {
	var url = $("#basePath").val()
			+ "module/appversioncheck/downloadNewestAppVersionRes?clientinfo=browser&versioncode=-1&appid="
			+ id;
	document.getElementById("win").style.display = "";
	$("#newdlurl").val(url);

	var urlbarcode = $("#basePath").val()
			+ "module/appversioncheck/downloadNewestAppVersionRes?clientinfo=barcode&versioncode=-1&appid="
			+ id;
	$.ajax({
		url : 'module/appversioncheck/genBarCode',
		type : 'post',
		dataType : 'json',
		data : {
			'appid':id,
			'url' : urlbarcode,
			'timestamp': new Date().getTime()
		},
		success : function(data) {
			var resultcode = data.result.resultcode;
			if (resultcode == 0) {
				$("#newdlurlbarcode").attr("src", data.result.obj);
			} else {
				alert(data.result.resultdesc);
			}
		}
	});
}

function hideUrlBar() {
	$("#newdlurlbarcode").attr("src", "");
	document.getElementById("win").style.display = "none";
}
$(document).ready(function() {
	$.ajax({
		url : 'module/appversioncheck/queryApp',
		type : 'post',
		dataType : 'json',
		data : {
			'appid': $("#appid").val()
		},
		success : function(data) {
			var appinfo = data.result.map.appinfo.appinfo;
			var appname = appinfo.appname;
			document.getElementById("apptitle").innerHTML = "发布<"+appname+">版本";
			$("#appname").val(appname);
		}
	});
});

function publicAppversioninfo() {
	var form = document.getElementById("form");
	var versioncode = $("#versioncode").val();
	var versioncodeautogen = $("#autogenvcode").find("option:selected").val();
	var versionname = $("#versionname").val();
	var updatelog = $("#updatelog").val();
	var updatetype = $("#updatetype").find("option:selected").val();
	var autoset = $("#autoset").find("option:selected").val();
	var downloadpath = $("#downloadpath").val();
	var autoinstall = $("#autoinstall").find("option:selected").val();
	var filename = $("#file").val();
	if (versioncodeautogen == 0 && versioncode == ""){
		alert("请输入版本号");
		return;
	}
	if (versionname == ""){
		alert("请输入版本名");
		return;
	}
	if (updatelog == ""){
		alert("请输入更新日志");
		return;
	}
	if (filename == "") {
		alert("请选择APP文件");
		return;
	}
	
	form.submit();
}
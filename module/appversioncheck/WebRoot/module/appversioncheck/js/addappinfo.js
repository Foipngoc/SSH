$(document).ready(function() {
	
});

function addappinfo(){
	var appname = $("#appname").val();
	var appdesc = $("#appdesc").val();
	if (appname == null || appname == ""){
		alert("应用名不能为空");
		return;
	}
	if (appdesc == null || appdesc == ""){
		alert("应用描述不能为空");
		return;
	}
	$.ajax({
		url : 'module/appversioncheck/publishApp',
		type : 'post',
		dataType : 'json',
		data : {
			'appname':appname,
			'appdesc':appdesc
		},
		success : function(data) {
			var resultcode = data.result.resultcode;
			var resultdesc = data.result.resultdesc;
			alert(resultdesc);
			if (resultcode == 0){
				window.location.href=$("#basePath").val()+"module/appversioncheck/appinfo.jsp";
			}
		}
	});
	
}
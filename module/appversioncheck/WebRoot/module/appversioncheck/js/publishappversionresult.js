$(document).ready(function() {
	var appid = $("#appid").val();
	var resultcode = $("#resultcode").val();
	if (resultcode == 0){
		alert("发布成功");
		window.location.href="module/appversioncheck/appversioninfo.jsp?appid="+appid;
	}else{
		if (resultcode == -1) {
			alert("参数错误");
		}else if (resultcode == 3) {
			alert("未找到应用");
		}else if (resultcode == 2) {
			alert("APP上传失败");
		}else if (resultcode == 4) {
			alert("文件保存失败");
		}else if (resultcode == 5) {
			alert("MD5校验失败");
		}else if (resultcode == 1) {
			alert("版本号必须大于当前发布版本中最大版本号");
		}else {
			alert("未知错误");
		}
		window.location.href="module/appversioncheck/addappversioninfo.jsp?appid="+appid;
		
	}
});
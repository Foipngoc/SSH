$(document).ready(function() {
	var appid = $("#appid").val();
	var resultcode = $("#resultcode").val();
	if (resultcode == 0){
		alert("修改成功");
		window.location.href="module/appversioncheck/appversioninfo.jsp?appid="+appid;
	}else{
		if (resultcode == 4) {
			alert("文件保存失败");
		}else if (resultcode == 5) {
			alert("MD5校验失败");
		}else if (resultcode == 1) {
			alert("版本不存在");
		}else {
			alert("未知错误");
		}
		window.location.href="module/appversioncheck/modifyappversion.jsp?appvid="+appvid+"&appid="+$("#appid").val();
		
	}
});
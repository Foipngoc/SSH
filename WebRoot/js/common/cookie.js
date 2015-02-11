//添加一个cookie：addCookie(name,value,expiresHours)
function addCookie(name,value,expiresHours) {
	var cookieString=name+"="+escape(value)+"; "; 
	// 获取当前时间
	var date = new Date();
	var expiresDays = 10; // 默认时间 将date设置为10天以后的时间
	if (expiresHours != null || expiresHours != undefined)
		expiresDays = expiresHours;

	date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000);
	// 判断是否设置过期时间
	if(expiresDays>0){
		document.cookie = cookieString+"expires="+ date.toGMTString();
	}
		
}
// 获取指定名称的cookie值：getCookie(name)
function getCookie(name){ 
	var strCookie=document.cookie; 
	var arrCookie=strCookie.split("; "); 
	for(var i=0;i<arrCookie.length;i++){ 
		var arr=arrCookie[i].split("="); 
		if(arr[0]==name)
			return arr[1]; 
	} 
	return ""; 
} 
// 删除指定名称的cookie：deleteCookie(name)
function deleteCookie(names) {
	var date=new Date(); 
	date.setTime(date.getTime()-10000); 
	for(var i=0;i<names.length;i++){
		var cookieString=names[i]+"v; ";
	}
	document.cookie=cookieString+"expires="+date.toGMTString(); 
}

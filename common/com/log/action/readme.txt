添加日志接口
	【接口】http://ip/project/loginfo/addlog
	【功能】添加日志
	【必选参数】
		 logInfo.logtype    日志类型，由业务系统自定
	【可选参数】
		 logInfo.loguser	日志用户
		 logInfo.logtitle	日志标题
		 logInfo.logcontent 日志内容
	【输出Json】成功状态 返回新添加的日志记录信息
	
删除日志接口
	【接口】http://ip/project/loginfo/dellog
	【功能】删除日志
	【必选参数】
		 logInfo.id 		日志ID
	【输出】无
	
查询所有日志接口
	【接口】http://ip/project/loginfo/querylogs
	【功能】查询日志
	【可选参数】
		 page 		页码
		 rows		每页日志数
	【输出Json】
		logInfos	日志列表
		pages		总页数
	
查询某条日志接口
	【接口】http://ip/project/loginfo/querylog
	【功能】查询某条日志
	【必选参数】
		logInfo.id 		日志ID
	【输出Json】
		logInfo			日志	
		
查询某类型日志接口
	【接口】http://ip/project/loginfo/querylogsbytype
	【功能】查询日志
	【必选参数】
		logInfo.logtype	日志类型
	【可选参数】
		 page 		页码
		 rows		每页日志数
	【输出Json】
		logInfos	日志列表
		pages		总页数
package com.common.action.result;

import java.util.Map;

/**
 * 失败结果
 * 
 * @author DJ
 * 
 */
public class BaseResultFailed extends BaseResult {
	public BaseResultFailed() {
		super(RESULT_FAILED, "未知原因");
	}

	public BaseResultFailed(String reason) {
		super(RESULT_FAILED, reason);
	}

	public BaseResultFailed(String reason, Map<String, Object> map) {
		super(RESULT_FAILED, reason, map);
	}

	public BaseResultFailed(String reason, Object obj) {
		super(RESULT_FAILED, reason, obj);
	}
}

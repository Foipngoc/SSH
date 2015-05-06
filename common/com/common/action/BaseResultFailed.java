package com.common.action;

public class BaseResultFailed extends BaseResult {
	public BaseResultFailed() {
		super(RESULT_FAILED, "未知原因");
	}

	public BaseResultFailed(String reason) {
		super(RESULT_FAILED, reason);
	}
}

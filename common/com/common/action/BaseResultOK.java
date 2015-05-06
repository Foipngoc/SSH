package com.common.action;

import com.common.dao.BaseQueryRecords;

public class BaseResultOK extends BaseResult {
	public BaseResultOK() {
		super(RESULT_OK, "");
	}

	public BaseResultOK(BaseQueryRecords<?> records) {
		super(RESULT_OK, "", records);
	}
}

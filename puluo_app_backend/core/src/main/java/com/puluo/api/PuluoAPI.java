package com.puluo.api;

import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.util.HasJSON;

public abstract class PuluoAPI<DSI extends PuluoDSI, RESULT extends HasJSON> {

	protected DSI dsi;
	protected RESULT rawResult;
	public ApiErrorResult error;

	public abstract void execute();

	public void setError(ApiErrorResult error) {
		this.error = error;
		this.rawResult = null;
	}
	
	public String result() {
		if (rawResult != null && error == null)
			return rawResult.toJson();
		else if (error != null) {
			return error.toJson();
		} else {
			return ApiErrorResult.getError(0).toJson();
		}
	}
}

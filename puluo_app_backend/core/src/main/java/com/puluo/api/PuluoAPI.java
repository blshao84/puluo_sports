package com.puluo.api;

import com.puluo.api.result.ApiErrorResult;
import com.puluo.util.HasJSON;

public abstract class PuluoAPI<R extends HasJSON> {
	
	protected R rawResult;

	public abstract void execute();
	
	public String result() {
		if (rawResult != null)
			return rawResult.toJson();
		else
			return new ApiErrorResult("no result","rawResult is null", "").toJson();

	}
}

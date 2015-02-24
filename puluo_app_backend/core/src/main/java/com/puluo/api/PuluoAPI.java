package com.puluo.api;

import com.puluo.api.result.ApiErrorResult;
import com.puluo.util.HasJSON;

public abstract class PuluoAPI<R extends HasJSON> {

	public abstract R rawResult();

	public String result() {
		R rawResult = rawResult();
		if (rawResult != null)
			return rawResult.toJson();
		else
			return new ApiErrorResult("no result","rawResult is null", "").toJson();

	}
}

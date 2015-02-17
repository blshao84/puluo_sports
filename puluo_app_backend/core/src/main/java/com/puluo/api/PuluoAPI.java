package com.puluo.api;

import com.puluo.util.HasJSON;


public abstract class PuluoAPI<R extends HasJSON> {
	
	public abstract R rawResult();
	
	public  String result() {
		return rawResult().toJson();
	}
}

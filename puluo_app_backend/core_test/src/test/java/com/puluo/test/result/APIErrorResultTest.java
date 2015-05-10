package com.puluo.test.result;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.puluo.result.ApiErrorResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class APIErrorResultTest {
	public static Log log = LogFactory.getLog(APIErrorResultTest.class);
	@Test
	public void testNoErrorIDCollision(){
		HashMap<Integer,List<ApiErrorResult>> map = new HashMap<Integer, List<ApiErrorResult>>();
		for(ApiErrorResult e:ApiErrorResult.allErrors()){
			if(map.containsKey(e.id)){
				List<ApiErrorResult> row = map.get(e.id);
				row.add(e);
				map.put(e.id,row);
			} else {
				List<ApiErrorResult> row = new ArrayList<ApiErrorResult>();
				row.add(e);
				map.put(e.id,row);
			}
		}
		for(Integer id:map.keySet()){
			List<ApiErrorResult> row = map.get(id);
			if(row.size()>1){
				for(ApiErrorResult e:row){
					log.info("api:"+e.id+" share the same id");
				}
			}
			Assert.assertEquals("no api error should share the same id",1, row.size());
		}
	}
}

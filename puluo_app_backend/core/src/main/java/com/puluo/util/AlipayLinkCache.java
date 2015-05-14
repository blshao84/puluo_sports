package com.puluo.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class AlipayLinkCache {
	public static Log log = LogFactory.getLog(AlipayLinkCache.class);
	private static Cache<String, String> cache = CacheBuilder.newBuilder()
			.maximumSize(10000)
			.removalListener(new RemovalListener<String, String>() {

				@Override
				public void onRemoval(
						RemovalNotification<String, String> notification) {
					log.info(String.format("remove uuid=%s,link=%s from cache",
							notification.getKey(), notification.getValue()));
				}

			}).expireAfterAccess(60, TimeUnit.SECONDS).build();
	
	public static void put(String uuid,String link) {
		cache.put(uuid, link);
	}
	
	public static String get(String uuid) {
		String res;
		res = cache.asMap().get(uuid);
		if(res==null) res = "";
		return res;
	}
}

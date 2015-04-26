package com.puluo.session;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.puluo.entity.PuluoSession;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoSessionManager {
	public static Log log = LogFactory.getLog(PuluoSessionManager.class);

	private static final Cache<String, PuluoSession> cache = CacheBuilder
			.newBuilder().maximumSize(10000)
			.removalListener(new RemovalListener<String, PuluoSession>() {
				@Override
				public void onRemoval(
						RemovalNotification<String, PuluoSession> notification) {
					log.info(String.format("remove toke=%s, user_uuid=%s ",
							notification.getKey(), notification.getValue()
									.userUUID()));
				}

			}).expireAfterAccess(30, TimeUnit.DAYS).build();

	public static void login(String key, PuluoSession session) {
		cache.put(key, session);
	}

	public static boolean isLogin(String key) {
		return cache.asMap().containsKey(key);
	}

	public static void logout(String key) {
		cache.invalidate(key);
	}

	public static PuluoSession getSession(String key) {
		return cache.asMap().get(key);
	}
	
	public static String getUserUUID(String token) {
		return getSession(token).userUUID();
	}
}

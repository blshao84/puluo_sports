package com.puluo.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.weichat.WeiChatUtil;

public class PuluoWechatTokenCache {
	private static Log log = LogFactory.getLog(PuluoWechatTokenCache.class);
	// nicely named threads are nice
	private static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
			.setNameFormat("MyCacheRefresher-pool-%d").setDaemon(true).build();
	private static final ExecutorService parentExecutor = Executors
			.newSingleThreadExecutor(threadFactory);

	// create an executor that provide ListenableFuture instances
	private static final ListeningExecutorService executorService = MoreExecutors
			.listeningDecorator(parentExecutor);

	private static final LoadingCache<WechatKey, String> cache = CacheBuilder
			.newBuilder().maximumSize(1)
			.refreshAfterWrite(7000, TimeUnit.SECONDS)
			.build(new CacheLoader<WechatKey, String>() {
				@Override
				public String load(WechatKey key) throws Exception {
					String token = WeiChatUtil.getAccessToken(key.wechat_id, key.wechat_key)
							.getToken();
					log.info(String.format(
							"get wechat token (%s) for AppId=%s,AppKey=%s",
							token, key.wechat_id, key.wechat_key));
					return null;
				}

				@Override
				public ListenableFuture<String> reload(final WechatKey key,
						final String oldToken) throws Exception {
					ListenableFuture<String> listenableFuture = executorService
							.submit(new Callable<String>() {
								@Override
								public String call() throws Exception {
									String newToken = load(key);
									log.info(String
											.format("refresh wechat key %s, oldToken=%s,newToken=%s",
													key, oldToken, newToken));
									return newToken;
								}
							});
					return listenableFuture;
				}
			});
}
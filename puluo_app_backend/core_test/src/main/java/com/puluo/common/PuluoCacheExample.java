package com.puluo.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class PuluoCacheExample {

	public static void main(String[] args) {
		final AtomicInteger value = new AtomicInteger(1);

		// nicely named threads are nice
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("MyCacheRefresher-pool-%d").setDaemon(true)
				.build();
		ExecutorService parentExecutor = Executors
				.newSingleThreadExecutor(threadFactory);

		// create an executor that provide ListenableFuture instances
		final ListeningExecutorService executorService = MoreExecutors
				.listeningDecorator(parentExecutor);

		final LoadingCache<String, Integer> cache = CacheBuilder
				.newBuilder()
				.maximumSize(1)
				// the cache will try to refresh any entry after it's first
				// written if it's accessed more than 10 seconds
				// after the last write
				.refreshAfterWrite(10, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(String key) throws Exception {
						System.out.println("RELOADING!");
						return value.getAndIncrement();
					}

					@Override
					public ListenableFuture<Integer> reload(final String key,
							Integer oldValue) throws Exception {
						// we need to load new values asynchronously, so that
						// calls to read values from the cache don't block
						ListenableFuture<Integer> listenableFuture = executorService
								.submit(new Callable<Integer>() {

									@Override
									public Integer call() throws Exception {
										System.out
												.println("Async reload event");
										return load(key);
									}
								});
						return listenableFuture;
					}
				});

		try {
			for (int i = 0; i < 100; i++) {
				long start = System.currentTimeMillis();
				Integer unchecked = cache.getUnchecked("foo");
				System.out.printf("Took %dms to load result %d: %d\n",
						(System.currentTimeMillis() - start), (i + 1),
						unchecked);

				Thread.sleep(1000);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.puluo.test.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.puluo.util.DSIResolver;

public class DSIResolverTest {
	@Test
	public void testResolveFromProperty() {
		String configFile;
		configFile = DSIResolver.resolveDSIConfig();
		Assert.assertEquals("puluo.xml",configFile);
		System.setProperty("run.mode", "production");
		configFile = DSIResolver.resolveDSIConfig();
		Assert.assertEquals("puluo-prod.xml",configFile);
		System.setProperty("run.mode", "functional");
		configFile = DSIResolver.resolveDSIConfig();
		Assert.assertEquals("puluo-functional.xml",configFile);
	}
	
	@Test
	public void testResolveFromEnv() {
		String configFile;
		configFile = testEnv("", "");
		Assert.assertEquals("puluo.xml",configFile);
		configFile = testEnv("PULUO_ENV", "");
		Assert.assertEquals("puluo.xml",configFile);
		configFile = testEnv("PULUO_ENV", "-Drun.mode=functional");
		Assert.assertEquals("puluo-functional.xml",configFile);
		configFile = testEnv("PULUO_ENV", "-Drun.mode=production");
		Assert.assertEquals("puluo.xml",configFile);
	}
	
	private String testEnv(String key, String value) {
		Map<String,String> env = new HashMap<String, String>();
		env.put(key, value);
		return DSIResolver.resolveFromEnv(env);
	}
}

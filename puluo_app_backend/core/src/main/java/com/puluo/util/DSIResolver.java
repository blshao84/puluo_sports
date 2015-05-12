package com.puluo.util;

import java.util.Map;

public class DSIResolver {
	private static Log log = LogFactory.getLog(DSIResolver.class);

	public static String resolveDSIConfig() {
		String fileName;
		String mode = System.getProperty("run.mode");
		if (mode != null && mode.equals("production")) {
			fileName = "puluo-prod.xml";
		} else if (mode != null && mode.equals("functional")) {
			fileName = "puluo-functional.xml";
		} else {
			// THE FOLLOWING IS A HACK!!!! TO USE FUNCTIONAL TEST ENV FOR jetty
			fileName = resolveFromEnv(System.getenv());
		}
		return fileName;
	}

	public static String resolveFromEnv(Map<String, String> env) {
		String fileName;
		String puluoEnv = env.get("PULUO_ENV");
		if (puluoEnv != null) {
			log.info("found PULUO_ENV is set to " + puluoEnv);
			String[] props = puluoEnv.split("=");
			if (props.length == 2) {
				if (props[0].equals("-Drun.mode")) {
					if (props[1].equals("functional")) {
						log.info("PULUO_ENV is set to functional, use puluo-functional.xml");
						fileName = "puluo-functional.xml";
					} else {
						log.info("unexpected PULUO_ENV properties,use puluo.xml");
						fileName = "puluo.xml";
					}
				} else {
					log.info("unexpected PULUO_ENV properties,use puluo.xml");
					fileName = "puluo.xml";
				}
			} else {
				log.info("unexpected PULUO_ENV properties,use puluo.xml");
				fileName = "puluo.xml";
			}
		} else {
			fileName = "puluo.xml";
		}
		return fileName;
	}
}

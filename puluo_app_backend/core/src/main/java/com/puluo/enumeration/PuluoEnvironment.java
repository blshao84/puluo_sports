package com.puluo.enumeration;

public enum PuluoEnvironment {
	PROD, DEV, QA;

	public static PuluoEnvironment getEnvironment() {
		String mode = System.getProperty("run.mode");
		if (mode == null) {
			return DEV;
		} else {
			if (mode.equals("production")) {
				return PROD;
			} else if (mode.equals("qa")) {
				return QA;
			} else {
				return DEV;
			}
		}
	}
}

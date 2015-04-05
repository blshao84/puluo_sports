package com.puluo.test.functional.util;


public abstract class EventFunctionalTestRunner implements
		PuluoAuthenticatedFunctionalTestRunner {
	abstract public EventTestDataSource dataSource();
	@Override
	public String mobile() {
		return dataSource().mobile;
	}

	@Override
	public String password() {
		return dataSource().password;
	}
}

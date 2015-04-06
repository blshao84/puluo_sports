package com.puluo.test.functional.util;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface PuluoAuthenticatedFunctionalTestRunner {
	public void run(String session) throws UnirestException ;
	public String mobile();
	public String password();
	public String inputs(String session);
}

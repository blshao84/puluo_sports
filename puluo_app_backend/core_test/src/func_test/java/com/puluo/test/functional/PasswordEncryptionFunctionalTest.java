package com.puluo.test.functional;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PasswordEncryptionUtil;

public class PasswordEncryptionFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(LogoutFunctionalTest.class);

	public static String mobile = "18521564305";
	public static String passwordText = "qqqqqq";
	public static String host = "http://localhost:3333/encode";

	@Test
	@Ignore
	public void testPasswordEncryption() {
		String passwd = PasswordEncryptionUtil.encrypt(passwordText);
		PuluoUser user = DaoApi.getInstance().userDao().getByMobile(mobile);
		Assert.assertNotNull(user);
		Assert.assertEquals(
				"password encryption between ui and server should match",
				user.password(), passwd);
	}
}

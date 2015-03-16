package com.puluo.test.api;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.puluo.api.auth.UserPasswordUpdateAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.MockTestDSI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoUserImpl;

public class PasswordUpdateAPITest {
	PuluoUserDao mockUserDao = Mockito.mock(PuluoUserDaoImpl.class);

	private class MockDSI extends MockTestDSI {
		@Override
		public PuluoUserDao userDao() {
			return mockUserDao;
		}
	}

	private final PuluoDSI mockDsi = new MockDSI();

	@Test
	public void testNullUser() {
		Mockito.when(mockUserDao.getByUUID(Matchers.anyString())).thenReturn(
				null);
		UserPasswordUpdateAPI api = new UserPasswordUpdateAPI("", "", "",
				mockDsi);
		api.execute();
		String expected = ApiErrorResult.getError(18).toJson();
		String actual = api.result();
		Assert.assertEquals("should return error result if can't find a user",
				expected, actual);
	}

	@Test
	public void testUnmatchOldPassword() {
		PuluoUser user = Mockito.mock(PuluoUserImpl.class);
		Mockito.when(user.password()).thenReturn("abcdefg");
		Mockito.when(mockUserDao.getByUUID(Matchers.anyString())).thenReturn(
				user);
		UserPasswordUpdateAPI api = new UserPasswordUpdateAPI("", "", "",
				mockDsi);
		api.execute();
		String expected = ApiErrorResult.getError(19).toJson();
		String actual = api.result();
		Assert.assertEquals(
				"should return error result if password doesnt' match",
				expected, actual);
	}

	@Test
	public void testUpdatePasswordFail() {
		PuluoUser user = Mockito.mock(PuluoUserImpl.class);
		Mockito.when(user.password()).thenReturn("abcdefg");
		Mockito.when(mockUserDao.getByUUID(Matchers.anyString())).thenReturn(
				user);
		Mockito.when(
				mockUserDao.updatePassword((PuluoUser) Matchers.anyObject(),
						Matchers.anyString())).thenReturn(false);
		UserPasswordUpdateAPI api = new UserPasswordUpdateAPI("", "abcdefg",
				"", mockDsi);
		api.execute();
		String expected = ApiErrorResult.getError(20).toJson();
		String actual = api.result();
		Assert.assertEquals(
				"should return error result if update password fail", expected,
				actual);
	}

	@Test
	public void testUpdatePasswordSucceed() {
		PuluoUser user = Mockito.mock(PuluoUserImpl.class);
		Mockito.when(user.password()).thenReturn("abcdefg");
		Mockito.when(mockUserDao.getByUUID(Matchers.anyString())).thenReturn(
				user);
		Mockito.when(
				mockUserDao.updatePassword((PuluoUser) Matchers.anyObject(),
						Matchers.anyString())).thenReturn(true);
		UserPasswordUpdateAPI api = new UserPasswordUpdateAPI("", "abcdefg",
				"gfedcba", mockDsi);
		api.execute();
		String expected = "{\"password\":\"abcdefg\",\"new_password\":\"gfedcba\"}";
		String actual = api.result();
		Assert.assertEquals(
				"successful update should return both new and old password",
				expected, actual);
	}
}

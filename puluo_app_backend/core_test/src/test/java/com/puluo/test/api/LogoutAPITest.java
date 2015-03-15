package com.puluo.test.api;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.puluo.api.auth.UserLogoutAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.MockTestDSI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoSessionImpl;
import com.puluo.entity.impl.PuluoUserImpl;

public class LogoutAPITest {
	PuluoUserDao mockUserDao = Mockito.mock(PuluoUserDaoImpl.class);
	PuluoSessionDao mockSessionDao = Mockito.mock(PuluoSessionDaoImpl.class);

	private class MockDSI extends MockTestDSI {
		@Override
		public PuluoUserDao userDao() {
			return mockUserDao;
		}

		@Override
		public PuluoSessionDao sessionDao() {
			return mockSessionDao;
		}
	}

	private final PuluoDSI mockDsi = new MockDSI();

	@Test
	public void testNullSession() {
		UserLogoutAPI api = new UserLogoutAPI(null);
		api.execute();
		String expected = ApiErrorResult.getError(13).toJson();
		String actual = api.result();
		Assert.assertEquals("can't delete a null session", expected, actual);
	}
	
	@Test
	public void testDeleteSession() {
		PuluoSession session = Mockito.mock(PuluoSessionImpl.class);
		Mockito.when(session.sessionID()).thenReturn("12345");
		Mockito.when(session.userMobile()).thenReturn("67890");
		Mockito.when(session.createdAt()).thenReturn(DateTime.now().minusDays(1));
		PuluoUser user = Mockito.mock(PuluoUserImpl.class);
		Mockito.when(user.userUUID()).thenReturn("abcdefg");
		Mockito.when(mockUserDao.getByMobile("67890")).thenReturn(user);
		Mockito.when(mockSessionDao.deleteSession("12345")).thenReturn(true);
		UserLogoutAPI api = new UserLogoutAPI(session,mockDsi);
		api.execute();
		String expected = "{\"uuid\":\"abcdefg\",\"duration_seconds\":86400}";
		String actual = api.result();
		Assert.assertEquals("successfully delete a session", expected, actual);
	}
	
	@Test
	public void testDeleteSessionFail() {
		PuluoSession session = Mockito.mock(PuluoSessionImpl.class);
		Mockito.when(session.sessionID()).thenReturn("12345");
		Mockito.when(session.userMobile()).thenReturn("67890");
		Mockito.when(session.createdAt()).thenReturn(DateTime.now().minusDays(1));
		PuluoUser user = Mockito.mock(PuluoUserImpl.class);
		Mockito.when(user.userUUID()).thenReturn("abcdefg");
		Mockito.when(mockUserDao.getByMobile("67890")).thenReturn(user);
		Mockito.when(mockSessionDao.deleteSession("12345")).thenReturn(false);
		UserLogoutAPI api = new UserLogoutAPI(session,mockDsi);
		api.execute();
		String expected =ApiErrorResult.getError(14).toJson();;
		String actual = api.result();
		Assert.assertEquals("fail to delete a session in DB", expected, actual);
	}

}

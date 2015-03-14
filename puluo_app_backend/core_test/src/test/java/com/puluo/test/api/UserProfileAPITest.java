package com.puluo.test.api;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.puluo.api.user.UserProfileAPI;
import com.puluo.dao.MockTestDSI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.impl.PuluoUserImpl;


public class UserProfileAPITest {

	private final PuluoUserDao mockUserDao = Mockito
			.mock(PuluoUserDaoImpl.class);

	private class MockDSI extends MockTestDSI {
		@Override
		public PuluoUserDao userDao() {
			return mockUserDao;
		}
	}

	private final PuluoDSI mockDsi = new MockDSI();

	private String uuid = "0228";

	
	@Test
	public void testUserProfileAPI() {
		PuluoUserImpl mockUser = Mockito.mock(PuluoUserImpl.class);
		PuluoUserDao mockUserDao =  Mockito.mock(PuluoUserDaoImpl.class);
		Mockito.when(mockDsi.userDao()).thenReturn(mockUserDao);
		Mockito.when(mockUserDao.getByUUID(Matchers.anyString())).thenReturn(mockUser);
		Mockito.when(mockUser.userUUID()).thenReturn(uuid);
		
		UserProfileAPI api = new UserProfileAPI(uuid,mockDsi);
		api.execute();

		String expected = "{"
				+ "\"uuid\":\"0228\","
				+ "\"public_info\":{" + "\"first_name\":\"\","
				+ "\"last_name\":\"\","
				+ "\"thumbnail\":\"\","
				+ "\"large_image\":\"\","
				+ "\"saying\":\"\"," + "\"likes\":,"
				+ "\"banned\":," + "\"following\":,"
				+ "\"is_coach\":" + "}," + "\"private_info\":{"
				+ "\"email\":\"\"," + "\"sex\":\"\","
				+ "\"birthday\":\"\","
				+ "\"occupation\":\"\","
				+ "\"country\":\"\"," + "\"state\":\"\","
				+ "\"city\":\"\"," + "\"zip\":\"\"" + "},"
				+ "\"created_at\":\"\","
				+ "\"updated_at\":\"\"" + "}";;
		Assert.assertEquals("UserProfileAPI should return the mocked userUUID()",expected,api.result());
	}
}
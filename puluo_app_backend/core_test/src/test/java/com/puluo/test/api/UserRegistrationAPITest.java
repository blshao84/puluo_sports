//package com.puluo.test.api;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.mockito.Matchers;
//import org.mockito.Mockito;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//
//import com.puluo.api.auth.UserRegistrationAPI;
//import com.puluo.api.result.ApiErrorResult;
//import com.puluo.dao.MockTestDSI;
//import com.puluo.dao.PuluoAuthCodeRecordDao;
//import com.puluo.dao.PuluoDSI;
//import com.puluo.dao.PuluoUserDao;
//import com.puluo.dao.PuluoUserSettingDao;
//import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;
//import com.puluo.dao.impl.PuluoUserDaoImpl;
//import com.puluo.dao.impl.PuluoUserSettingDaoImpl;
//import com.puluo.entity.PuluoAuthCodeRecord;
//import com.puluo.entity.PuluoUser;
//import com.puluo.entity.impl.PuluoAuthCodeRecordImpl;
//import com.puluo.entity.impl.PuluoUserImpl;
//
//public class UserRegistrationAPITest {
//	private final PuluoUserDao mockUserDao = Mockito
//			.mock(PuluoUserDaoImpl.class);
//	private final PuluoUserSettingDao mockSettingDao = Mockito
//			.mock(PuluoUserSettingDaoImpl.class);
//	private final PuluoAuthCodeRecordDao mockAuthRecordDao = Mockito
//			.mock(PuluoAuthCodeRecordDaoImpl.class);
//
//	private class MockDSI extends MockTestDSI {
//		@Override
//		public PuluoUserDao userDao() {
//			return mockUserDao;
//		}
//
//		@Override
//		public PuluoAuthCodeRecordDao authCodeRecordDao() {
//			return mockAuthRecordDao;
//		}
//
//		@Override
//		public PuluoUserSettingDao userSettingDao() {
//			return mockSettingDao;
//		}
//	}
//
//	private final PuluoDSI mockDsi = new MockDSI();
//
//	private final String mobile = "1234567";
//	private final String password = "abcdefg";
//	private final String auth_code = "1234";
//
//	@Test
//	public void testRegisterExistingUser() {
//		PuluoUser mockUser = Mockito.mock(PuluoUserImpl.class);
//		Mockito.when(mockUserDao.getByMobile(Matchers.anyString())).thenReturn(
//				mockUser);
//		String actual = run();
//		String expected = ApiErrorResult.getError(5).toJson();
//		Assert.assertEquals("existing user can't register", expected, actual);
//
//	}
//
//	@Test
//	public void testNewUserWithoutAuthCode() {
//		Mockito.when(mockUserDao.getByMobile(Matchers.anyString())).thenReturn(
//				null);
//		Mockito.when(
//				mockAuthRecordDao.getRegistrationAuthCodeFromMobile(mobile))
//				.thenReturn(null);
//		String actual = run();
//		String expected = ApiErrorResult.getError(6).toJson();
//		Assert.assertEquals("user can't register without an auth code",
//				expected, actual);
//
//	}
//
//	@Test
//	public void testNewUserWithWrongAuthCode() {
//		PuluoAuthCodeRecord mockAuthRecord = Mockito
//				.mock(PuluoAuthCodeRecordImpl.class);
//		Mockito.when(mockAuthRecord.authCode()).thenReturn("5678");
//		Mockito.when(mockUserDao.getByMobile(Matchers.anyString())).thenReturn(
//				null);
//		Mockito.when(
//				mockAuthRecordDao.getRegistrationAuthCodeFromMobile(mobile))
//				.thenReturn(mockAuthRecord);
//		String actual = run();
//		String expected = ApiErrorResult.getError(7).toJson();
//		Assert.assertEquals("user can't register with a wrong auth code",
//				expected, actual);
//
//	}
//
//	@Test
//	public void testNewUserSaveFailure() {
//		PuluoAuthCodeRecord mockAuthRecord = Mockito
//				.mock(PuluoAuthCodeRecordImpl.class);
//		Mockito.when(mockAuthRecord.authCode()).thenReturn("1234");
//		Mockito.when(mockUserDao.getByMobile(Matchers.anyString())).thenAnswer(
//				new MockFindByMobileAnswer());
//		Mockito.when(mockUser.userUUID()).thenReturn("123456");
//		Mockito.when(
//				mockAuthRecordDao.getRegistrationAuthCodeFromMobile(mobile))
//				.thenReturn(mockAuthRecord);
//		Mockito.when(mockSettingDao.saveNewSetting(Matchers.anyString())).thenReturn(true);
//
//		String actual = run();
//		String expected = ApiErrorResult.getError(8).toJson();
//		Assert.assertEquals("encounter error when save user", expected, actual);
//	}
//
//	private String run() {
//		UserRegistrationAPI api = new UserRegistrationAPI(mobile, password,
//				auth_code, mockDsi);
//		api.execute();
//		return api.result();
//	}
//
//	static PuluoUser mockUser = Mockito.mock(PuluoUserImpl.class);
//
//	public static class MockFindByMobileAnswer implements Answer<PuluoUser> {
//		public static int invocationCount = 0;
//
//		@Override
//		public PuluoUser answer(InvocationOnMock invocation) throws Throwable {
//			if (invocationCount == 0){
//				invocationCount++;
//				return null;
//			}else {
//				return mockUser;
//			}
//		}
//
//	}
//}

package com.puluo.test.api;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.service.PuluoSMSType;
import com.puluo.api.service.SMSServiceAPI;
import com.puluo.dao.MockTestDSI;
import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;
import com.puluo.service.JuheSMSClient;
import com.puluo.service.PuluoService;
import com.puluo.service.util.JuheSMSResult;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PuluoService.class)
public class ServiceAPITest {
	private final PuluoAuthCodeRecordDao mockAuthCodeRecordDao = Mockito
			.mock(PuluoAuthCodeRecordDaoImpl.class);

	private class MockDSI extends MockTestDSI {
		@Override
		public PuluoAuthCodeRecordDao authCodeRecordDao() {
			return mockAuthCodeRecordDao;
		}
	}
	
	private final MockDSI mockDSI = new MockDSI();

	@Test
	public void testSuccessfulSend() {
		PowerMockito.mockStatic(PuluoService.class);
		JuheSMSClient client = Mockito.mock(JuheSMSClient.class);
		JuheSMSResult smsResult = Mockito.mock(JuheSMSResult.class);
		Mockito.when(PuluoService.getSms()).thenReturn(client);
		Mockito.when( 
				client.sendAuthCode(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(smsResult);
		Mockito.when(smsResult.isSuccess()).thenReturn(true);
		Mockito.when(
				mockAuthCodeRecordDao.upsertRegistrationAuthCode(
						Matchers.anyString(), Matchers.anyString()))
				.thenReturn(true);
		SMSServiceAPI api = new SMSServiceAPI(PuluoSMSType.UserRegistration,
				"123456", new HashMap<String, String>(),mockDSI);
		api.execute();
		String actual = api.result();
		String expected = "{\"mobile\":\"123456\",\"status\":\"success\"}";
		Assert.assertEquals("should successful send result",expected,actual);
	}
	
	@Test
	public void testFailedSend() {
		PowerMockito.mockStatic(PuluoService.class);
		JuheSMSClient client = Mockito.mock(JuheSMSClient.class);
		JuheSMSResult smsResult = Mockito.mock(JuheSMSResult.class);
		Mockito.when(PuluoService.getSms()).thenReturn(client);
		Mockito.when( 
				client.sendAuthCode(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(smsResult);
		Mockito.when(smsResult.isSuccess()).thenReturn(false);
		SMSServiceAPI api = new SMSServiceAPI(PuluoSMSType.UserRegistration,
				"123456", new HashMap<String, String>(),mockDSI);
		api.execute();
		String actual = api.result();
		String expected = ApiErrorResult.getError(9).toJson();
		Assert.assertEquals("should fail to send sms",expected,actual);
	}
	
	@Test
	public void testFailedSave() {
		PowerMockito.mockStatic(PuluoService.class);
		JuheSMSClient client = Mockito.mock(JuheSMSClient.class);
		JuheSMSResult smsResult = Mockito.mock(JuheSMSResult.class);
		Mockito.when(PuluoService.getSms()).thenReturn(client);
		Mockito.when( 
				client.sendAuthCode(Matchers.anyString(), Matchers.anyString()))
				.thenReturn(smsResult);
		Mockito.when(smsResult.isSuccess()).thenReturn(true);
		Mockito.when(
				mockAuthCodeRecordDao.upsertRegistrationAuthCode(
						Matchers.anyString(), Matchers.anyString()))
				.thenReturn(false);
		SMSServiceAPI api = new SMSServiceAPI(PuluoSMSType.UserRegistration,
				"123456", new HashMap<String, String>(),mockDSI);
		api.execute();
		String actual = api.result();
		String expected = ApiErrorResult.getError(10).toJson();
		Assert.assertEquals("should fail to save code",expected,actual);
	}
}

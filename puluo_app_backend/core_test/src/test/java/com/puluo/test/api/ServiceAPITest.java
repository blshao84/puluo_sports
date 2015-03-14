package com.puluo.test.api;

import org.mockito.Mockito;

import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;

public class ServiceAPITest {
	private final PuluoAuthCodeRecordDao mockAuthCodeRecordDao = Mockito
			.mock(PuluoAuthCodeRecordDaoImpl.class);
}

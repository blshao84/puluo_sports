package com.puluo.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import com.puluo.api.DemoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.impl.PuluoUserImpl;


public class DemoAPITest {

	@Test
	public void testDemoAPI() {
		String msg = "5678";
		PuluoUserImpl mockUser = Mockito.mock(PuluoUserImpl.class);
		PuluoUserDao mockUserDao =  Mockito.mock(PuluoUserDaoImpl.class);
		PuluoDSI mockDsi = Mockito.mock(DaoApi.class);
		Mockito.when(mockDsi.userDao()).thenReturn(mockUserDao);
		Mockito.when(mockUserDao.getByMobile(Matchers.anyString())).thenReturn(mockUser);
		Mockito.when(mockUser.idUser()).thenReturn(msg);
		DemoAPI api = new DemoAPI(msg,mockDsi);
		api.execute();
		String expected = "{\"result\":\"5678:5678\"}";
		assertEquals("DemoAPI should return the mocked idUser()",expected,api.result());
	}
}

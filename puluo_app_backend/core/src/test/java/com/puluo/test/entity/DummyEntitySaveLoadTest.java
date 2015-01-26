package com.puluo.test.entity;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.impl.DaoTestApi;
import com.puluo.dao.impl.PgDummyDaoImpl;
import com.puluo.dao.impl.BeanTestFactory;
import com.puluo.entity.PgDummy;
import com.puluo.entity.impl.PgDummyImpl;

public class DummyEntitySaveLoadTest {
	@BeforeClass
	public static void setUpDB() {
		PgDummyImpl dummy1 = new PgDummyImpl(1L, "a");
		PgDummyImpl dummy2 = new PgDummyImpl(2L, "b");
		DaoTestApi.pgDummy.createTable();
		DaoTestApi.pgDummy.save(dummy1);
		DaoTestApi.pgDummy.save(dummy2);
	}

	@Test
	public void testEntityLoad() {
		PgDummy dummy1 = DaoTestApi.pgDummy.getById(1L);
		PgDummy dummy2 = DaoTestApi.pgDummy.getById(2L);
		assertEquals("dummy1's id should be a", dummy1.id().longValue(), 1L);
		assertEquals("dummy2's id should be a", dummy2.id().longValue(), 2L);
		assertEquals("dummy1's name should be a", dummy1.name(), "a");
		assertEquals("dummy2's name should be b", dummy2.name(), "b");
	}

}

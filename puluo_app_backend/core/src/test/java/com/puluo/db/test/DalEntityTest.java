package com.puluo.db.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import com.puluo.db.impl.PgDummyDao;
import com.puluo.entity.PgDummy;
import com.puluo.entity.impl.PgDummyImpl;
import com.puluo.util.TestBeanFactory;

public class DalEntityTest {
	@BeforeClass
	public static void setUpDB() {
		PgDummyImpl dummy1 = new PgDummyImpl(1L, "a");
		PgDummyImpl dummy2 = new PgDummyImpl(2L, "b");
		PgDummyDao dummyDao = TestBeanFactory.getBean(PgDummyDao.class,
				"pgDummyDao");
		dummyDao.createTable();
		dummyDao.save(dummy1);
		dummyDao.save(dummy2);
	}

	@Test
	public void testEntityLoad() {
		PgDummyDao dummyDao = TestBeanFactory.getBean(PgDummyDao.class,
				"pgDummyDao");
		PgDummy dummy1 = dummyDao.getById(1L);
		PgDummy dummy2 = dummyDao.getById(2L);
		assertEquals("dummy1's id should be a", dummy1.id().longValue(), 1L);
		assertEquals("dummy2's id should be a", dummy2.id().longValue(), 2L);
		assertEquals("dummy1's name should be a", dummy1.name(), "a");
		assertEquals("dummy2's name should be b", dummy2.name(), "b");
	}

}

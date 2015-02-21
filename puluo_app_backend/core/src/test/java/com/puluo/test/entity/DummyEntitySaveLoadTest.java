package com.puluo.test.entity;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PgDummyDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PgDummy;
import com.puluo.entity.impl.PgDummyImpl;

public class DummyEntitySaveLoadTest {
	@BeforeClass
	public static void setUpDB() {
		populateDB(DaoTestApi.pgInMemDummy);
	}

	@Test
	public void testInMemEntityLoad() {
		PgDummy dummy1 = DaoTestApi.pgInMemDummy.getById(1L);
		PgDummy dummy2 = DaoTestApi.pgInMemDummy.getById(2L);
		assertEquals("dummy1's id should be a", dummy1.id().longValue(), 1L);
		assertEquals("dummy2's id should be a", dummy2.id().longValue(), 2L);
		assertEquals("dummy1's name should be a", dummy1.name(), "a");
		assertEquals("dummy2's name should be b", dummy2.name(), "b");
	}

	@Test
	public void testFileEntityLoad() {
		PgDummy dummy1 = DaoTestApi.pgFileDummy.getById(1L);
		PgDummy dummy2 = DaoTestApi.pgFileDummy.getById(2L);
		assertEquals("dummy1's id should be a", dummy1.id().longValue(), 1L);
		assertEquals("dummy2's id should be a", dummy2.id().longValue(), 2L);
		assertEquals("dummy1's name should be a", dummy1.name(), "a");
		assertEquals("dummy2's name should be b", dummy2.name(), "b");
	}

	public static void main(String[] args) {
		populateFileDB();
	}

	private static void populateFileDB() {
		populateDB(DaoTestApi.pgFileDummy);
	}

	private static void populateDB(PgDummyDao dao) {
		PgDummyImpl dummy1 = new PgDummyImpl(1L, "a");
		PgDummyImpl dummy2 = new PgDummyImpl(2L, "b");
		dao.createTable();
		dao.save(dummy1);
		dao.save(dummy2);
	}

}

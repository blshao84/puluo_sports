package com.puluo.db.test;

import org.junit.Assert;
import org.junit.Test;
import com.puluo.db.PgDummy;
import com.puluo.db.impl.PgDummyImpl;
import com.puluo.util.TestBeanFactory;



public class DalEntityTest {
	@Test
	public  void testEntityInit() {
		 PgDummy dummy = TestBeanFactory.getBean(PgDummyImpl.class, "PgDummyEntity");
		 Long id = dummy.id();
		 String name = dummy.name();
		 Assert.assertEquals("dummy entity's id should be 1",1L, id.longValue());
		 Assert.assertEquals("dummy entity's name should be test","test", name);
		 Assert.assertEquals("dummy entity's toString should be pgdummy-test:1test","pgdummy-test:1test", dummy.toString());
	}
	
	@Test
	public void testEntitySave() {
		
	}

}

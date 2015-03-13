package com.puluo.test.payment;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.puluo.config.Configurations;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.payment.alipay.AlipayUtil;

public class AlipayUtilTest {
	@Test
	public void testValidTradeIDConversion() {
		long orderID = 12345L;
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		Mockito.when(order.orderNumericID()).thenReturn(orderID);
		String idString = AlipayUtil.generateOrderID(order,
				Configurations.orderIDBase);
		long parsedID = AlipayUtil.parseOrderID(idString,
				Configurations.orderIDBase);
		Assert.assertEquals(
				"should be able to parse back the original order ID", orderID,
				parsedID);
	}
	
	@Test
	public void testInvalidIDConversion(){
		try {
			AlipayUtil.parseOrderID("123a4",
					Configurations.orderIDBase);
			Assert.fail("should not be able to parse invalid id");
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}
}

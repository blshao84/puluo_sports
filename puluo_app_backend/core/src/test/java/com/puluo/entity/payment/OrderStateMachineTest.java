package com.puluo.entity.payment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.entity.payment.impl.OrderEventType;
import com.puluo.entity.payment.impl.PuluoOrderStateMachine;
import com.puluo.entity.payment.impl.PuluoOrderStatus;

public class OrderStateMachineTest {

	@Test
	public void testFromUndefined() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.Undefined);

		verifyDefinedEvent(order, OrderEventType.PlaceOrderEvent,
				PuluoOrderStatus.New);

		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.CompleteOrderEvent,
				OrderEventType.ConfirmOrderEvent,
				OrderEventType.DropOrderEvent, OrderEventType.PayOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	@Test
	public void testFromNew() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.New);

		verifyDefinedEvent(order, OrderEventType.PayOrderEvent,
				PuluoOrderStatus.Paying);

		verifyDefinedEvent(order, OrderEventType.DropOrderEvent,
				PuluoOrderStatus.Cancel);

		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.CompleteOrderEvent,
				OrderEventType.ConfirmOrderEvent,
				OrderEventType.PlaceOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	@Test
	public void testFromPaying() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.Paying);

		verifyDefinedEvent(order, OrderEventType.ConfirmOrderEvent,
				PuluoOrderStatus.Paid);

		verifyDefinedEvent(order, OrderEventType.DropOrderEvent,
				PuluoOrderStatus.New);

		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.CompleteOrderEvent,
				OrderEventType.PayOrderEvent, OrderEventType.PlaceOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	@Test
	public void testFromPaid() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.Paid);

		verifyDefinedEvent(order, OrderEventType.CompleteOrderEvent,
				PuluoOrderStatus.Complete);

		verifyDefinedEvent(order, OrderEventType.DropOrderEvent,
				PuluoOrderStatus.Cancel);

		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.ConfirmOrderEvent, OrderEventType.PayOrderEvent,
				OrderEventType.PlaceOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	@Test
	public void testFromComplete() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.Complete);
		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.CompleteOrderEvent,
				OrderEventType.ConfirmOrderEvent,
				OrderEventType.DropOrderEvent, OrderEventType.PayOrderEvent,
				OrderEventType.PlaceOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	@Test
	public void testFromCancel() {
		PuluoPaymentOrder order = mock(PuluoPaymentOrderImpl.class);
		when(order.status()).thenReturn(PuluoOrderStatus.Cancel);
		OrderEventType[] undefinedEvents = { OrderEventType.CancelOrderEvent,
				OrderEventType.CompleteOrderEvent,
				OrderEventType.ConfirmOrderEvent,
				OrderEventType.DropOrderEvent, OrderEventType.PayOrderEvent,
				OrderEventType.PlaceOrderEvent };
		verifyUndefinedEvents(order, undefinedEvents);
	}

	private void verifyUndefinedEvents(PuluoPaymentOrder order,
			OrderEventType[] undefinedEvents) {
		OrderEvent event = mock(OrderEventImpl.class);
		for (OrderEventType orderEventType : undefinedEvents) {
			when(event.eventType()).thenReturn(orderEventType);
			PuluoOrderStatus newStatus2 = PuluoOrderStateMachine.nextState(
					order, event);
			assertEquals(
					String.format("%s doesn't define event for %s",
							order.status(), event.eventType()),
					PuluoOrderStatus.Undefined, newStatus2);
		}
	}

	private void verifyDefinedEvent(PuluoPaymentOrder order,
			OrderEventType definedEvent, PuluoOrderStatus expectedStatus) {
		OrderEvent event = mock(OrderEventImpl.class);
		when(event.eventType()).thenReturn(definedEvent);
		PuluoOrderStatus newStatus1 = PuluoOrderStateMachine.nextState(order,
				event);
		assertEquals(String.format("%s --%s--> %s", order.status(),
				definedEvent, expectedStatus), expectedStatus, newStatus1);
	}
}

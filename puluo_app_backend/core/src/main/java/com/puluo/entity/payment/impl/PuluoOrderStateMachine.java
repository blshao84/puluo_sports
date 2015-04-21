package com.puluo.entity.payment.impl;

import java.util.List;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.enumeration.PuluoOrderStatus;

public class PuluoOrderStateMachine {
	public static PuluoOrderStatus nextState(PuluoPaymentOrder order,
			List<OrderEvent> events) {
		PuluoOrderStatus currentStatus = order.status();
		return nextState(currentStatus, events);
	}

	public static PuluoOrderStatus nextState(PuluoOrderStatus currentStatus,
			List<OrderEvent> events) {
		PuluoOrderStatus nextStatus = currentStatus;
		for (OrderEvent orderEvent : events) {
			nextStatus = nextState(nextStatus, orderEvent);
		}
		return nextStatus;
	}

	public static PuluoOrderStatus nextState(PuluoPaymentOrder order,
			OrderEvent event) {
		PuluoOrderStatus currentStatus = order.status();
		return nextState(currentStatus, event);
	}

	public static PuluoOrderStatus nextState(PuluoOrderStatus currentStatus,
			OrderEvent event) {
		PuluoOrderStatus nextStatus = PuluoOrderStatus.Undefined;
		if (event == null)
			return nextStatus;
		else {
			switch (currentStatus) {
			case Undefined:
				nextStatus = fromUndefinedState(event);
				break;

			case New:
				nextStatus = fromNewState(event);
				break;

			case Paying:
				nextStatus = fromPayingState(event);
				break;

			case Paid:
				nextStatus = fromPaidState(event);
				break;

			case Complete:
				nextStatus = fromCompleteState(event);
				break;

			case Cancel:
				nextStatus = fromCancelState(event);
				break;
			}

			return nextStatus;
		}
	}

	private static PuluoOrderStatus fromCancelState(OrderEvent event) {
		return PuluoOrderStatus.Undefined;
	}

	private static PuluoOrderStatus fromCompleteState(OrderEvent event) {
		return PuluoOrderStatus.Undefined;

	}

	private static PuluoOrderStatus fromPaidState(OrderEvent event) {
		PuluoOrderStatus status = PuluoOrderStatus.Undefined;
		switch (event.eventType()) {
		case CompleteOrderEvent:
			status = PuluoOrderStatus.Complete;
			break;
		case DropOrderEvent:
			status = PuluoOrderStatus.Cancel;
			break;
		default:
			status = PuluoOrderStatus.Undefined;
			break;
		}

		return status;

	}

	private static PuluoOrderStatus fromPayingState(OrderEvent event) {
		PuluoOrderStatus status = PuluoOrderStatus.Undefined;
		switch (event.eventType()) {
		case ConfirmOrderEvent:
			status = PuluoOrderStatus.Paid;
			break;
		case DropOrderEvent:
			status = PuluoOrderStatus.New;
			break;
		default:
			status = PuluoOrderStatus.Undefined;
			break;
		}

		return status;
	}

	private static PuluoOrderStatus fromNewState(OrderEvent event) {
		PuluoOrderStatus status = PuluoOrderStatus.Undefined;
		switch (event.eventType()) {
		case PayOrderEvent:
			status = PuluoOrderStatus.Paying;
			break;
		case DropOrderEvent:
			status = PuluoOrderStatus.Cancel;
			break;
		default:
			status = PuluoOrderStatus.Undefined;
			break;
		}

		return status;

	}

	private static PuluoOrderStatus fromUndefinedState(OrderEvent event) {
		PuluoOrderStatus status = PuluoOrderStatus.Undefined;
		switch (event.eventType()) {
		case PlaceOrderEvent:
			status = PuluoOrderStatus.New;
			break;
		default:
			status = PuluoOrderStatus.Undefined;
			break;
		}

		return status;

	}
}

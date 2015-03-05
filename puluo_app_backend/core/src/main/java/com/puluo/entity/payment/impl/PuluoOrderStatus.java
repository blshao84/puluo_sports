package com.puluo.entity.payment.impl;

/**
 * Undefined: Any unexpected state, eq to 'null' state
 * 
 * New: After a new order is placed
 * 
 * Paying: Go to 3rd party payment service
 *
 * Paid: Receive payment confirmation from 3rd party payment service
 * 
 * Complete: Event is over (marked by user)
 * 
 * Cancel: After user cancel the event and payment
 * 
 * @author blshao
 *
 */
public enum PuluoOrderStatus {
	Undefined, New, Paying, Paid, Complete, Cancel;

	public boolean isCancel() {
		return (this == Cancel);
	}

	public boolean isPaid() {
		
		return ((this == Paid) || (this == Complete));
	}
}

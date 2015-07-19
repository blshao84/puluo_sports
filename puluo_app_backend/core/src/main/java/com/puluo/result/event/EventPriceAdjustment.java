package com.puluo.result.event;


public class EventPriceAdjustment {
	//private static final Log log = LogFactory.getLog(EventPriceAdjustment.class);
	
	private final String msg;
	private final Double oldValue;
	private final Double newValue;
	
	public EventPriceAdjustment(String msg, Double oldValue, Double newValue) {
		super();
		this.msg = msg;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result
				+ ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result
				+ ((oldValue == null) ? 0 : oldValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventPriceAdjustment other = (EventPriceAdjustment) obj;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		return true;
	}
	
	
}

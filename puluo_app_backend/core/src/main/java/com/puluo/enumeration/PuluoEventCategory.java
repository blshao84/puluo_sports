package com.puluo.enumeration;

public enum PuluoEventCategory {
	Stamina("体能"),Yoga("瑜伽"),Dance("舞蹈"),Health("塑形调理"),Others("其他");
	private final String desc;
	
	private PuluoEventCategory(String desc){
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}

package com.puluo.enumeration;

public enum PuluoEventLevel {
	Level1("初级"),Level2("中级"),Level3("高级"),Level4("其他");
	
	private final String desc;
	
	private PuluoEventLevel(String desc){
		this.desc = desc;
	}
	
	@Override
	public String toString(){
		return desc;
	}
}

package com.puluo.enumeration;

public enum SortDirection {
	Desc("desc"), Asc("asc");
	private final String direction;

	private SortDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return direction;
	}
}

package com.puluo.entity.impl;

import com.puluo.entity.PgDummy;

public class PgDummyImpl implements PgDummy {
	private String name;
	private Long id;
	
	public PgDummyImpl() {}
	
	public PgDummyImpl(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public PgDummyImpl(String name) {
		this.name = name;
	}
	
	protected String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Long id() {
		// TODO Auto-generated method stub
		return id;
	}

	public String toString() {
		return  id().toString() + name();
	}

}

package com.puluo.db.impl;

import com.puluo.db.PgDummy;
import com.puluo.jdbc.DalTemplate;

public class PgDummyImpl extends DalTemplate implements PgDummy{
	private String name;
	private Long id;
	
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
		return this.getFullTableName()+":"+id().toString() + name();
	}

}

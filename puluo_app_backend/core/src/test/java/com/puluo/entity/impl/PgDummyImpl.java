package com.puluo.entity.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.puluo.entity.PgDummy;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.jdbc.SqlWriter;
import com.puluo.util.PuluoException;

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

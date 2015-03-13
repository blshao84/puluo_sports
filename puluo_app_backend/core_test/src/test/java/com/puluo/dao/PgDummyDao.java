package com.puluo.dao;

import com.puluo.entity.PgDummy;

public interface PgDummyDao {
	public void createTable();
	public Long save(PgDummy entity);
	public PgDummy getById(Long id);
}

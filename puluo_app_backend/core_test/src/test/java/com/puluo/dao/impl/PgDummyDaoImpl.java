package com.puluo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.puluo.dao.PgDummyDao;
import com.puluo.entity.PgDummy;
import com.puluo.entity.impl.PgDummyImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.jdbc.SqlReader;
import com.puluo.jdbc.SqlWriter;

public class PgDummyDaoImpl extends DalTemplate implements PgDummyDao{

	@Override
	public void createTable() {
		String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName()).append(" (id long, name varchar(100))")
				.toString();
		getWriter().execute(createSQL);
	}

	@Override
	public Long save(PgDummy entity) {
		SqlWriter writer = getWriter();
		PgDummy dummy = getById(entity.id());
		if (dummy == null) {
			final String insertSQL = new StringBuilder().append("insert into ")
					.append(super.getFullTableName())
					.append(" (id, name) values ( ?,? )").toString();
			writer.update(insertSQL, entity.id(), entity.name());
			return entity.id();
		} else
			return dummy.id();
	}

	@Override
	public PgDummy getById(Long id) {
		SqlReader reader = getReader();
		String selectSQL = new StringBuilder().append("select * from ")
				.append(super.getFullTableName()).append(" where id = ?")
				.toString();
		List<PgDummy> entities = reader.query(selectSQL, new Object[] { id },
				new RowMapper<PgDummy>() {
					@Override
					public PgDummy mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						PgDummyImpl dummy = new PgDummyImpl();
						dummy.setId(rs.getLong("id"));
						dummy.setName(rs.getString("name"));
						return dummy;
					}
				});
		return verifyUniqueResult(entities);
	}
}

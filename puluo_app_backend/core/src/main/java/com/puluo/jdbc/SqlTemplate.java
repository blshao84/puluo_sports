package com.puluo.jdbc;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import com.puluo.util.Log;
import com.puluo.util.LogFactory;

/**
 * 用于扩展jdbcTemplate，只用于mysql的扩展
 * 
 * @author mefan
 * 
 */
public class SqlTemplate extends JdbcTemplate implements SqlReader, SqlWriter {
	private static final Log log = LogFactory.getLog(SqlTemplate.class);

	@Override
	public DataSource getDataSource() {
		return super.getDataSource();
	}

	@Override
	public boolean ensureUpdate(String sql) throws DataAccessException {
		int updatedRows = this.update(sql);
		if (updatedRows == 0) {
			log.error("no row updated ...");
			return false;
		} else
			return true;
	}

}

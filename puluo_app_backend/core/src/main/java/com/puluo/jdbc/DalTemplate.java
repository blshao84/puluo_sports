package com.puluo.jdbc;

import java.util.List;

import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public abstract class DalTemplate {
	private static Log log = LogFactory.getLog(DalTemplate.class);
	
	private List<SqlWriter> writers;
	private List<SqlReader> readers;
	private int writerSize;
	private int readerSize;
	private String fullTableName;

	public void setFullTableName(String fullTableName) {
		this.fullTableName = fullTableName;
	}

	public String getFullTableName() {
		return fullTableName;
	}

	public SqlReader getReader() {
		SqlReader reader = readers
				.get(((Long) (System.currentTimeMillis() % readerSize))
						.intValue());
		return reader;
	}

	public SqlWriter getWriter() {
		SqlWriter writer = writers
				.get(((Long) (System.currentTimeMillis() % writerSize))
						.intValue());
		return writer;
	}

	public void setReaders(final List<SqlReader> readers) {
		this.readers = readers;
		readerSize = readers.size();
	}

	public void setWriters(final List<SqlWriter> writers) {
		this.writers = writers;
		writerSize = writers.size();
	}

	protected boolean deleteByUniqueKey(String key, String value) {
		try {
			String deleteSQL = new StringBuilder().append("delete from ")
					.append(getFullTableName())
					.append(" where ")
					.append(key).append(" = '").append(value)
					.append("'").toString();
			log.info(deleteSQL);
			getWriter().execute(deleteSQL);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;

	}

}

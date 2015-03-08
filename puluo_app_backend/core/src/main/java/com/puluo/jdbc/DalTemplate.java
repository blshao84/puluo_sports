package com.puluo.jdbc;

import java.util.List;


public abstract class DalTemplate
{

    private List<SqlWriter> writers;
    private List<SqlReader> readers;
    private int writerSize;
    private int readerSize;
    private String fullTableName;

    public void setFullTableName(String fullTableName) {
		this.fullTableName = fullTableName;
	}

	public String getFullTableName()
    {
        return fullTableName;
    }

    public SqlReader getReader()
    {
        SqlReader reader = readers.get(((Long) (System.currentTimeMillis() % readerSize)).intValue());
        return reader;
    }

    public SqlWriter getWriter()
    {
        SqlWriter writer = writers.get(((Long) (System.currentTimeMillis() % writerSize)).intValue());
        return writer;
    }

    public void setReaders(final List<SqlReader> readers)
    {
        this.readers = readers;
        readerSize = readers.size();
    }



    public void setWriters(final List<SqlWriter> writers)
    {
        this.writers = writers;
        writerSize = writers.size();
    }

}

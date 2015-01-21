package com.puluo.jdbc;

import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.KeyHolder;

public interface SqlWriter
{
    DataSource getDataSource();
    public int[] inserts(String tableName, String[] cols, Collection<Object[]> values, int splitCount);

    public int[] inserts(String tableName, String[] cols, Collection<Object[]> values, int splitCount, boolean isIgnoreRepeat);

    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException;

    public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss);

    public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes);

    public int[] batchUpdate(String sql, List<Object[]> batchArgs);

    public int[] batchUpdate(String[] sql) throws DataAccessException;

    public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException;

    public <T> T execute(ConnectionCallback<T> action) throws DataAccessException;

    public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException;

    public <T> T execute(StatementCallback<T> action) throws DataAccessException;

    public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException;

    public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException;

    public void execute(String sql) throws DataAccessException;

    public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException;

    public int update(PreparedStatementCreator psc) throws DataAccessException;

    public int update(String sql, Object... args) throws DataAccessException;

    public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException;

    public int update(String sql, PreparedStatementSetter pss) throws DataAccessException;

    public int update(String sql) throws DataAccessException;

}

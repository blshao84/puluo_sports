package com.puluo.jdbc;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class JdbcTemplate extends org.springframework.jdbc.core.JdbcTemplate
{

    /**
     * 通过sql查询一行结果
     * 
     * @author mefan
     * @param <T>
     * @param sql
     * @param args
     * @param rowMapper
     * @param isElseNull
     *            为true时，如果没有数据则返回null,不然抛出异常EmptyResultDataAccessException
     * @return
     */
    public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper, boolean isElseNull)
    {
        if (isElseNull)
        {
            try
            {
                return super.queryForObject(sql, args, rowMapper);
            }
            catch (EmptyResultDataAccessException e)
            {
                return null;
            }
        }
        return super.queryForObject(sql, args, rowMapper);
    }

    public <T> T queryForObject(String sql, RowMapper<T> rowMapper, boolean isElseNull, Object... args)
    {
        return queryForObject(sql, args, rowMapper, isElseNull);
    }

}

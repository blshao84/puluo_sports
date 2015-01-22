package com.puluo.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.puluo.util.ArrayUtils;
import com.puluo.util.Colls;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;


/**
 * 用于扩展jdbcTemplate，只用于mysql的扩展
 * 
 * @author mefan
 * 
 */
public class SqlTemplate extends JdbcTemplate implements SqlReader,SqlWriter
{
    private static final Log LOG = LogFactory.getLog(SqlTemplate.class);
 
    @Override
    public DataSource getDataSource()
    {
        return super.getDataSource();
    }

    /**
     * insert into table_name values (value,value),(value,value)...
     * 
     * @author mefan
     * @param tableName
     * @param values
     * @param splitCount
     *            分批处理，一次多少个，最优参数需要自己调试
     * @return
     */
    public int[] inserts(String tableName, String[] cols, Collection<Object[]> values, int splitCount)
    {
        return inserts(tableName, cols, values, splitCount, true);
    }

    /**
     * 
     * @author mefan
     * @param tableName
     * @param cols
     * @param values
     * @param splitCount
     * @param isIgnoreRepeat当为true
     *            ,如果存在唯一性冲突，该记录被忽视
     * @return
     */
    public int[] inserts(String tableName, String[] cols, Collection<Object[]> values, int splitCount, boolean isIgnoreRepeat)
    {
        // if(splitCount>999)
        // throw new LeJianException("inserts超出上限了，哥们");
        if (Colls.isEmpty(values))
            return new int[] {};

        int length = values.iterator().next().length;// 获取有几个值
        String base = getBaseInsertSQL(tableName, cols, isIgnoreRepeat);// insert
                                                                        // into
                                                                        // tableName(col1,col2)

        String placeholder = getPlaceholder(length);// 获取一个插入所需要的占位符
        Collection<Collection<Object[]>> splitColl = Colls.split(values, splitCount, LinkedList.class);// 任务分割

        int[] results = new int[splitColl.size()];
        int count = 0;
        for (Collection<Object[]> collection : splitColl)
        {
            StringBuilder builder = new StringBuilder(base);
            List list = new ArrayList();
            for (Object[] objects : collection)
            {
                builder.append(placeholder);
                builder.append(",");
                for (Object object : objects)
                {
                    list.add(object);
                }
            }
            builder.delete(builder.length() - 1, builder.length());
            String sql = builder.toString();// 生成最后的sql

            results[count] = this.update(sql, list.toArray());// 执行sql

        }
        return results;
    }

    private String getColsSQL(String[] cols)
    {
        return Strs.join("(", Strs.join(cols, ","), ")");
    }

    private String getPlaceholder(int length)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (int i = 0; i < length; i++)
        {
            builder.append("?,");
        }
        int builderLength = builder.length();
        builder.delete(builderLength - 1, builderLength);
        builder.append(")");
        return builder.toString();
    }

    private String getBaseInsertSQL(String tableName, String[] cols, boolean isIgnoreRepeat)
    {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert ");
        if (isIgnoreRepeat)
            sqlBuilder.append("ignore");
        sqlBuilder.append(" into ").append(tableName);
        if (!ArrayUtils.isEmpty(cols))
            sqlBuilder.append(getColsSQL(cols));
        sqlBuilder.append(" values");

        String base = sqlBuilder.toString();
        return base;
    }
}

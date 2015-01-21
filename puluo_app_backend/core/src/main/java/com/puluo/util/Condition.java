package com.puluo.util;

public abstract class Condition
{

    /**
     * 运行时断言
     * 
     * @author mefan
     * @param test
     *            测试条件
     * @param messages
     *            失败消息
     */
    public static void assertion(boolean test, Object... messages)
    {
        if (!test)
        {
            throw new LeJianException(Strs.join(messages));
        }
    }
}

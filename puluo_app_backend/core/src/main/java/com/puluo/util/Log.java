package com.puluo.util;

/**
 * 日志工具封装类<br>
 * 严禁使用System.out.做输出，迁移到web容器时，可能导致日志丢失
 * 
 * @author mefan
 * 
 */
public interface Log
{

    public void debug(Object message);

    public void debug(Throwable t, Object... messages);

    public void debug(Object... messages);

    public void info(Throwable t, Object... messages);

    public void info(Object... messages);

    public void debug(Object message, Throwable t);

    void debug(Throwable t);

    public void error(Object... messages);

    public void error(Throwable t, Object... messages);

    public void error(Object message, Throwable t);

    void error(Throwable t);

    public void error(Object message);

    public void warn(Throwable t, Object... messages);

    void warn(Throwable t);

    public void warn(Object... messages);

    public void warn(Object message, Throwable t);

    public void warn(Object message);

    public void info(Object message, Throwable t);

    void info(Throwable t);

    public void info(Object message);

    public boolean isDebugEnabled();

    public boolean isInfoEnabled();
}

package com.puluo.util;


import org.slf4j.LoggerFactory;
/**
 * 用于产生Log对象 
 * @author mefan
 *
 */
public abstract class LogFactory
{
    /**
     * 获取某个类的日志实例
     * @author mefan
     * @param clazz
     * @return
     */
    public static Log getLog(final Class clazz)
    {
        return new Log()
        {
            private final org.slf4j.Logger log = LoggerFactory.getLogger(clazz);

            public void debug(Throwable t, Object... messages)
            {
                if (isDebugEnabled())
                {
                    this.debug(Strs.join(messages), t);
                }
            }

            public void debug(Object... messages)
            {
                if (isDebugEnabled())
                {
                    this.debug(Strs.join(messages));
                }
            }

            public void info(Throwable t, Object... messages)
            {
                if (isInfoEnabled())
                    this.info(Strs.join(messages), t);
            }

            public void info(Object... messages)
            {
                if (isInfoEnabled())
                    info(Strs.join(messages));
            }

            public void debug(Object message, Throwable t)
            {
                log.debug(message.toString(), t);
            }

            public void debug(Object message)
            {
                log.debug(message.toString());
            }

            public void error(Object... messages)
            {
                log.error(Strs.join(messages));
            }

            public void error(Throwable t, Object... messages)
            {
                log.error(Strs.join(messages), t);
            }

            public void error(Object message, Throwable t)
            {
                log.error(message.toString(), t);
            }

            public void error(Object message)
            {
                log.error(message.toString());
            }

            public void warn(Throwable t, Object... messages)
            {
                log.warn(Strs.join(messages), t);
            }

            public void warn(Object... messages)
            {
                log.warn(Strs.join(messages));
            }

            public void warn(Object message, Throwable t)
            {
                log.warn(message.toString(), t);
            }

            public void warn(Object message)
            {
                log.warn(message.toString());
            }

            public void info(Object message, Throwable t)
            {
                log.info(message.toString(), t);
            }

            public void info(Object message)
            {
                log.info(message.toString());
            }

            public boolean isDebugEnabled()
            {
                return log.isDebugEnabled();
            }

            public boolean isInfoEnabled()
            {
                return log.isInfoEnabled();
            }

            @Override
            public void debug(Throwable t)
            {
                log.debug("", t);
            }

            @Override
            public void error(Throwable t)
            {
                log.error("", t);
            }

            @Override
            public void warn(Throwable t)
            {
                log.warn("", t);
            }

            @Override
            public void info(Throwable t)
            {
                log.info("", t);
            }

        };
    }

    public static void main(String[] args)
    {
        LogFactory.getLog(LogFactory.class).error(new RuntimeException());
    }
}

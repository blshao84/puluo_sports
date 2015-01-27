package com.puluo.util;

import org.springframework.context.ApplicationContext;

/**
 * beanFactory 提供bean容器，封装bean的生命周期及字节码生成
 * 
 * @author mefan
 * 
 */
public abstract class BeanFactoryHelper 
{
    private static final Log LOGGER = LogFactory.getLog(BeanFactoryHelper.class);
    
	/**
     * 指定类型和bean名取得具体的实例
     * 
     * @author mefan
     * @param <T>
     * @param beanClass
     * @param beanName
     * @return
     */
    public static <T> T getBean(ApplicationContext context,Class<T> beanClass, String... beanName)
    {
        if (!ArrayUtils.isEmpty(beanName))
        {
            String bean = buildBeanName(beanName);

            return context.getBean(bean, beanClass);
        }
        Object bean = context.getBean(beanClass);
        return (T) bean;
    }

    private static String buildBeanName(String... beanName)
    {
        String bean = null;
        if (beanName.length > 1)
        {
            bean = Strs.join(beanName);
        }
        else
        {
            bean = beanName[0];
        }
        return bean;
    }

    /**
     * 通过beanName取得bean,自转型
     * 
     * @author mefan
     * @param <T>
     * @param beanId
     * @return
     */
    public static <T> T getBean(ApplicationContext context,String beanId)
    {
        return (T) context.getBean(beanId);
    }
    

}

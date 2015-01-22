package com.puluo.util;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * beanFactory 提供bean容器，封装bean的生命周期及字节码生成
 * 
 * @author mefan
 * 
 */
public abstract class TestBeanFactory {
	private static final Log LOGGER = LogFactory.getLog(BeanFactory.class);
	private static final ApplicationContext context;

	private static final String fileName = "puluo-test.xml";

	static {
		context = new ClassPathXmlApplicationContext(fileName);
	}

	public static <T> T getBean(Class<T> beanClass, String... beanName) {
		return BeanFactoryHelper.getBean(context, beanClass, beanName);
	}

	public static <T> T getBean(String beanId) {
		return BeanFactoryHelper.getBean(context, beanId);
	}

}

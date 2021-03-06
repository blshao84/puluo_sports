package com.puluo.dao.impl;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.puluo.util.ArrayUtils;
import com.puluo.util.BeanFactoryHelper;
import com.puluo.util.DSIResolver;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

/**
 * beanFactory 提供bean容器，封装bean的生命周期及字节码生成
 * 
 * @author mefan
 * 
 */
public abstract class BeanFactory {
	private static final Log log = LogFactory.getLog(BeanFactory.class);
	private static final ApplicationContext context;

	private static final String fileName;

	static {
		fileName = DSIResolver.resolveDSIConfig();
		File dir = new File("");
		log.info("DIR=" + dir.getAbsolutePath());
		log.info("configuration file:" + fileName);
		if (dir.isDirectory() && !ArrayUtils.isEmpty(dir.listFiles())) {
			log.info("reading the config file {} from {}", fileName, dir);
			context = new FileSystemXmlApplicationContext(fileName);
		} else {
			context = new ClassPathXmlApplicationContext(fileName);
		}
	}

	public static <T> T getBean(Class<T> beanClass, String... beanName) {
		return BeanFactoryHelper.getBean(context, beanClass, beanName);
	}

	public static <T> T getBean(String beanId) {
		return BeanFactoryHelper.getBean(context, beanId);
	}

}

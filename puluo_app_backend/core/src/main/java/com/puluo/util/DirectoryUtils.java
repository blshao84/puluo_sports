package com.puluo.util;

import java.io.File;

public abstract class DirectoryUtils {
	private final static String APP_DIR;
	static{
		File file=new File("");
		APP_DIR=file.getAbsolutePath();
	}
	/**
	 * 获取当前程序的运行目录(控制台)
	 * @author mefan
	 * @return
	 */
	public static String getAppDir(){
		return APP_DIR;
	} 
}

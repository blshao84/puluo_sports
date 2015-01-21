package com.puluo.util;

import java.io.File;

public abstract class Files
{
    private final static String APP_DIR;
    static
    {
        File file = new File("");
        APP_DIR = file.getAbsolutePath();
    }
    
    /**
     * 获取当前程序的运行目录(控制台)
     * 
     * @author mefan
     * @return
     */
    public static String getAppDir()
    {
        return APP_DIR;
    }
    
    public static boolean hasFileInAppDir(String fileName)
    {
        return isExists(Strs.join(APP_DIR, "\\", fileName));
    }
    
    public static boolean isExists(String filePath)
    {
        File file = new File(filePath);
        return file.exists();
    }
}

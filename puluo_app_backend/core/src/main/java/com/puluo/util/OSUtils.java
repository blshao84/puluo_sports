package com.puluo.util;

import java.util.Properties;

public final class OSUtils
{
    private static boolean isWindowOS;
    private static String osArch;
    private static String osName;
    private static String osVersion;
    static
    {
        final Properties props = System.getProperties(); // 获得系统属性集
        OSUtils.osName = props.getProperty("os.name"); // 操作系统名称
        OSUtils.osArch = props.getProperty("os.arch"); // 操作系统构架
        OSUtils.osVersion = props.getProperty("os.version"); // 操作系统版本
        OSUtils.isWindowOS = OSUtils.osName.contains("Window");
    }
    
    public static String getOSName()
    {
        return OSUtils.osName;
    }
    
    public static long getPID()
    {
        final String processName = java.lang.management.ManagementFactory.getRuntimeMXBean()
                .getName();
        return Long.parseLong(processName.split("@")[0]);
        
    }
    
    public static boolean isWindowOS()
    {
        return OSUtils.isWindowOS;
    }
    
    public static void main(final String[] args) throws InterruptedException
    {
        System.out.println(OSUtils.getPID());
        
        System.out.println(OSUtils.getOSName());
    }
    
}

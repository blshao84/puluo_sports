package com.puluo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩工具
 * 
 * @author mefan
 * 
 */
public class ZipUtil
{
    // private final static JsonHPack jcompHPack = new JsonHPack();
    
    // 压缩
    public static String compress(String str)
    {
        try
        {
            if (Strs.isEmpty(str)) { return str; }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    
    // 测试方法
    public static void main(String[] args) throws IOException
    {
        String temp = "l;jsafljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看safljsdoeiuoksjdfpwrp3oiruewoifrjewflk我的得到喀喀喀 看看看看看看看看  ";
        System.out.println("原字符串=" + temp);
        System.out.println("原长=" + temp.length());
        String temp1 = ZipUtil.compress(temp);
        System.out.println("压缩后的字符串=" + temp1);
        System.out.println("压缩后的长=" + temp1.length());
        System.out.println("解压后的字符串=" + ZipUtil.uncompress(temp1));
    }
    
    // public static String compressJsonWithHPack(String str)
    // {
    // String compJsonString = jcompHPack.pack(str);
    // return ZipUtil.compress(compJsonString);
    // }
    //
    // public static String unCompressJsonWithHPack(String str)
    // {
    // String unGzipString = ZipUtil.uncompress(str);
    // return jcompHPack.unpack(unGzipString);
    // }
    
    // 解压缩
    public static String uncompress(String str)
    {
        try
        {
            if (Strs.isEmpty(str)) { return str; }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gunzip.read(buffer)) >= 0)
            {
                out.write(buffer, 0, n);
            }
            // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
            return out.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
    
}
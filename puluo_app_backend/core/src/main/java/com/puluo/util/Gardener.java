package com.puluo.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Gardener
{

    // private static final Log LOG=LogFactory.getLog(Gardener.class);
    public static void init()
    {
        // ICT配置文件的路径."."代表当前工作目录.如果不明确建议写绝对路径
        String argu = "/data/gardener/dict/";
        // ICT分词加载程序.如果分词初始化失败程序将停止继续运行
        // 一般程序初始化失败的可能性是没有在路径中找到被指文件
        if (ICTCLAS2011.ICTCLAS_Init(argu.getBytes(), 1) == false)
        {
            System.out.println("Init Fail!");
            System.exit(-1);
        }
        System.out.println("init ok");
    }

    private static final ExecutorService helper = Executors.newSingleThreadExecutor();

    public static String[] split(final String str)
    {
        try
        {
            return helper.submit(new Callable<String[]>()
            {

                @Override
                public String[] call() throws Exception
                {
                    ICTCLAS2011 testICTCLAS2010 = new ICTCLAS2011();

                    /*
                     * 设置词性标注集 ID 代表词性集 1 计算所一级标注集 0 计算所二级标注集 2 北大二级标注集 3
                     * 北大一级标注集
                     */
                    testICTCLAS2010.ICTCLAS_SetPOSmap(2);

                    String nativeStr = Strs.BLANK;

                    // 导入用户词典前
                    byte nativeBytes[] = testICTCLAS2010.ICTCLAS_ParagraphProcess(str.getBytes("UTF-8"), 0);
                    nativeStr = new String(nativeBytes, 0, nativeBytes.length, "UTF-8");

                    // System.out.println("未导入用户词典： " + nativeStr);
                    return nativeStr.split(" ");
                }
            }).get();
        }
        catch (InterruptedException e)
        {
            System.out.println(e);
        }
        catch (ExecutionException e)
        {
            System.out.println(e);
        }
        return new String[] {};
    }

}

package com.puluo.util;

/**
 * 系统级异常基类
 * 
 * @author mefan
 * 
 */
public class PuluoException extends RuntimeException
{
    private static final String DEFAULT = "assert fail";
    
    public PuluoException(final Object... joinMessage)
    {
        super(Strs.join(ArrayUtils.isEmpty(joinMessage) ? PuluoException.DEFAULT : Strs
                .join(joinMessage)));
    }
    
}

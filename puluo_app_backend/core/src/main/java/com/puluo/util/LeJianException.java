package com.puluo.util;

/**
 * 系统级异常基类
 * 
 * @author mefan
 * 
 */
public class LeJianException extends RuntimeException
{
    private static final String DEFAULT = "assert fail";
    
    public LeJianException(final Object... joinMessage)
    {
        super(Strs.join(ArrayUtils.isEmpty(joinMessage) ? LeJianException.DEFAULT : Strs
                .join(joinMessage)));
    }
    
}

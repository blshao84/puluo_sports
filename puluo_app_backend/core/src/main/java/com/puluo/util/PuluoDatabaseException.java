package com.puluo.util;

public class PuluoDatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1287113178821688368L;
	private static final String DEFAULT = "assert fail";
    
    public PuluoDatabaseException(final Object... joinMessage) {
        super(Strs.join(ArrayUtils.isEmpty(joinMessage) ? 
        		PuluoDatabaseException.DEFAULT : Strs.join(joinMessage)));
    }
    
}

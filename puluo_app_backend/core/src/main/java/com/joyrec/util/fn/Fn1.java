package com.joyrec.util.fn;

import java.io.Serializable;

public interface Fn1<R, P1> extends Serializable
{
    R exec(P1 parm) throws Exception;
}

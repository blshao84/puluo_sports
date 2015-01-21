package com.puluo.util;

/**
 * 三元素的元组
 * 
 * @author mefan
 * 
 * @param <E1>
 * @param <E2>
 * @param <E3>
 */
public class Tuple3<E1, E2, E3> extends Pair<E1, E2>
{

    private E3 third;

    public Tuple3(E1 first, E2 second, E3 third)
    {
        super(first, second);
        this.third = third;
    }

    public E3 getThird()
    {
        return third;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == null || !(other instanceof Tuple3))
            return false;
        return super.equals(other) && third == null ? ((Tuple3) other).third == null : ((Tuple3) other).third.equals(third);
    }

}

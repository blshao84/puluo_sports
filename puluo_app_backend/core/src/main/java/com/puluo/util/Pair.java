package com.puluo.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 * 一个拥有两个元素的元组
 * 
 * @author zangcy00
 */
public class Pair<E1, E2> implements Serializable
{
    public E1 first;
    public E2 second;

    public Pair(E1 first, E2 second)
    {
        super();
        this.first = first;
        this.second = second;
    }

    public E1 First()
    {
        return first;
    }

    public E2 Second()
    {
        return second;
    }

    @Override
    public int hashCode()
    {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Pair)
        {
            Pair otherPair = (Pair) other;
            return ((this.first == otherPair.first || (this.first != null && otherPair.first != null && this.first
                    .equals(otherPair.first))) && (this.second == otherPair.second || (this.second != null
                    && otherPair.second != null && this.second.equals(otherPair.second))));
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

}

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
	private static final long serialVersionUID = 1L;
	public final E1 first;
    public final E2 second;

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	@Override
    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }

}

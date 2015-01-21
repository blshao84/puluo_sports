/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puluo.math;

/**
 *
 * @author hd2210
 */
public class BasicMath {
    /**
     * Find a prime which is greater than n.
     * @param n
     * @return
     */
    public static int findPrime(int n){
        while(true){
            if(isPrime(n))
                return n;
            n++;
        }
    }

    /**
     * Decide whether n is a prime.
     * @param n
     * @return
     */
    public static boolean isPrime(int n){

        for (long i = 3; i <= Math.sqrt(n); i += 2){
            if (n % i == 0)
                return false;
        }

        if (( n%2 !=0 && n > 2) || n == 2)
            return true;

        return false;
    }
}

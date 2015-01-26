/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.puluo.util;

import java.util.Map;

/**
 *
 * @author hd2210
 */
public class MapAlgebra {//FIXME:工具类建议为抽象类

    public static void multiply(Map<String, Double> user_interest, double dis) {
        for(Map.Entry<String,Double> entity : user_interest.entrySet()){
            double x = entity.getValue();
            entity.setValue(x*dis);
        }
    }

     public static void linearCombine(Map<String, Double> user_interest, Map<String,Double> add_interest, double dis) {
        for(Map.Entry<String,Double> entity : add_interest.entrySet()){
            double x = entity.getValue() * dis;
            String key = entity.getKey();
            if(user_interest.containsKey(key)){
                x += user_interest.get(key);
            }
            user_interest.put(key, x);
        }
    }

     public static double innerProduct(Map<String,Double> map1, Map<String,Double> map2){
         double sum = 0;
         for(Map.Entry<String,Double> entity : map1.entrySet()){
             String key = entity.getKey();
             double value1 = entity.getValue();
             if(map2.containsKey(key)){
                 double value2 = map2.get(key);
                 sum += value1 * value2;
             }
         }
         return sum;
     }

     public static double Norm2(Map<String,Double> map){
         double sum = 0;
         for(double value : map.values()){
             sum += value * value;
         }
         return Math.sqrt(sum);
     }

}

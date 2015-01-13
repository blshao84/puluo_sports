package com.puluo.example;

import java.util.Comparator;
import com.puluo.util.PinyinComparator;


public class PinyinExample {
	public static void main(String args[]) {
		
        testCommon();
        System.out.println("testCommon succeeds");
        testDifferentLength();
        System.out.println("testDifferentLength succeeds");
        testMultiSound();
        System.out.println("testMultiSound succeeds");
        testNoneChinese();
        System.out.println("testNoneChinese succeeds");
        testNoneCommon();
        System.out.println("testNoneCommon succeeds");
        testSameSound();
        System.out.println("testSameSound succeeds");
    } 
	
	private static Comparator<String> comparator = new PinyinComparator();

    /**
     * 常用字
     */
    
    public static void testCommon() {
        assert(comparator.compare("孟", "宋") < 0);
    }

    /**
     * 不同长度
     */
    
    public static void testDifferentLength() {
        assert(comparator.compare("他奶奶的", "他奶奶的熊") < 0);
    }

    /**
     * 和非汉字比较
     */
    
    public static void testNoneChinese() {
        assert(comparator.compare("a", "阿") < 0);
        assert(comparator.compare("1", "阿") < 0);
    }

    /**
     * 非常用字(怡)
     */
    
    public static void testNoneCommon() {
        assert(comparator.compare("怡", "张") < 0);
    }

    /**
     * 同音字
     */
    
    public static void testSameSound() {
        assert(comparator.compare("怕", "帕") == 0);
    }

    /**
     * 多音字(曾)
     */
    
    public static void testMultiSound() {
        assert(comparator.compare("曾经", "曾迪") > 0);
    }
}

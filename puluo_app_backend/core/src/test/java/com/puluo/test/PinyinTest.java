package com.puluo.test;

import java.util.Comparator;

import org.junit.Test;

import com.puluo.util.PinyinComparator;

public class PinyinTest {

	private static Comparator<String> comparator = new PinyinComparator();

	/**
	 * 常用字
	 */
	@Test
	public void testCommon() {
		assert (comparator.compare("猛","宋") < 0);
	}

	/**
	 * 不同长度
	 */
	@Test
	public void testDifferentLength() {
		assert (comparator.compare("他奶奶的", "他奶奶的熊") < 0);
	}

	/**
	 * 和非汉字比较
	 */
	@Test
	public void testNoneChinese() {
		assert (comparator.compare("a", "阿") < 0);
		assert (comparator.compare("1", "阿") < 0);
	}

	/**
	 * 非常用字(怡)
	 */
	@Test
	public void testNoneCommon() {
		assert (comparator.compare("怡","张") < 0);
	}

	/**
	 * 同音字
	 */
	@Test
	public void testSameSound() {
		assert (comparator.compare("怕","帕") == 0);
	}

	/**
	 * 多音字(曾)
	 */
	@Test
	public void testMultiSound() {
		assert (comparator.compare("曾经","曾迪") > 0);
	}
}

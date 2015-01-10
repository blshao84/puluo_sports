package com.unuotech.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class PinyinSimpleComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        return Collator.getInstance(Locale.CHINESE).compare(o1, o2);
    }
}


package com.starlord.payment.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtilsWrapper {

    private static final String ZERO = "0";

    public static String leftPaddingWithBlank(String text, int length) {
//        String result = StringUtils.leftPad(text, length, "_");
        String result = StringUtils.leftPad(text, length, StringUtils.SPACE);
        return result == null ? StringUtils.EMPTY : result;
    }

    public static String leftPaddingWithZero(String text, int length) {
        String result =  StringUtils.leftPad(text, length, ZERO);
        return result == null ? StringUtils.EMPTY : result;
    }

    public static String rightPaddingWithBlank(String text, int length) {
//        String result =  StringUtils.rightPad(text, length, "_");
        String result =  StringUtils.rightPad(text, length, StringUtils.SPACE);
        return result == null ? StringUtils.EMPTY : result;
    }

    public static String rightPaddingWithZero(String text, int length) {
        String result =  StringUtils.rightPad(text, length, ZERO);
        return result == null ? StringUtils.EMPTY : result;
    }
}

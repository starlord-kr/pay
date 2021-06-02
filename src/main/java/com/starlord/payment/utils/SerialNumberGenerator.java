package com.starlord.payment.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class SerialNumberGenerator {
    private static final Integer SERIAL_NUMBER_LENGTH = 20;

    public static String getSerialNumber() {
        return RandomStringUtils.randomAlphanumeric(SERIAL_NUMBER_LENGTH);
    }
}

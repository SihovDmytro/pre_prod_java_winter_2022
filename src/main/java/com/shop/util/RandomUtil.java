package com.shop.util;

import java.util.Calendar;
import java.util.Random;

public class RandomUtil {
    private static final int RANDOM_MIN_INT = 0;
    private static final int RANDOM_MAX_INT = 100000;
    private static final int RANDOM_CODE_NUMBERS = 8;
    private static final int RANDOM_YEAR_RANGE = 5;

    public static String randomString(String text) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < RANDOM_CODE_NUMBERS; i++) {
            code.append(randomInt(0, 10));
        }
        return text + " " + code;
    }

    public static int randomInt() {
        return randomInt(RANDOM_MIN_INT, RANDOM_MAX_INT);
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static Calendar randomCalendar() {
        Calendar randCalendar = Calendar.getInstance();

        randCalendar.add(Calendar.YEAR, randomInt(-RANDOM_YEAR_RANGE, RANDOM_YEAR_RANGE));
        return randCalendar;
    }


}

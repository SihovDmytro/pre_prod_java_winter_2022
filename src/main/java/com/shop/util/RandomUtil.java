package com.shop.util;

import java.util.Random;

public class RandomUtil {
    private static final int RANDOM_MIN_INT = 0;
    private static final int RANDOM_MAX_INT = 100000;


    public static String randomString(String text) {
        return text + " " + (new Random().nextLong());
    }

    public static int randomInt() {
        return randomInt(RANDOM_MIN_INT, RANDOM_MAX_INT);
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

}

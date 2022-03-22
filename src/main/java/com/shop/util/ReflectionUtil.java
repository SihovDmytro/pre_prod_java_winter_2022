package com.shop.util;

public class ReflectionUtil {
    public static String getSetterName(String field) {

        return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}

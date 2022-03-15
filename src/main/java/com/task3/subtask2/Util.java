package com.task3.subtask2;

import java.util.function.Function;

public class Util {
    public static final Function<String, Integer> returnStringLength = (String::length);
    public static final Function<String, Integer> returnSumOfFirstFourChars = ((txt) -> {
        int hash = 0;
        for (int i = 0; i < 4 && i < txt.length(); i++) {
            hash += txt.charAt(i);
        }
        return hash;
    });
}

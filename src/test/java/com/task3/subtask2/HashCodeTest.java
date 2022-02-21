package com.task3.subtask2;

import com.task1.subtask1.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HashCodeTest {
    private HashMap<MyString, Product> hashMap = new HashMap<>();
    private LinkedHashMap<String, Product> linkedHashMap = new LinkedHashMap<>();

    @Test
    public void shouldReturnHashCodeUsingFirstAlgorithm()
    {
        MyString1 myString1 = new MyString1("some string");

        int expected = 11;
        int actual = myString1.hashCode();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnHashCodeUsingSecondAlgorithm()
    {
        MyString2 myString2 = new MyString2("some string");

        int expected = 's'+'o'+'m'+'e';
        int actual = myString2.hashCode();

        Assertions.assertEquals(expected,actual);
    }
}

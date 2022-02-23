package com.task3.subtask2;

import com.task1.subtask1.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModifiedStringTest {

    @Test
    public void shouldReturnHashCodeUsingFirstAlgorithm()
    {
        ModifiedString modifiedString = new ModifiedString("some string");

        int expected = 11;
        int actual = modifiedString.hashCode();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnHashCodeUsingSecondAlgorithm()
    {
        ModifiedString modifiedString2 = new ModifiedString("some string");
        modifiedString2.switchAlgorithm();

        int expected = 's'+'o'+'m'+'e';
        int actual = modifiedString2.hashCode();

        Assertions.assertEquals(expected,actual);
    }
}

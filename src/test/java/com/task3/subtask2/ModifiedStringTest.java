package com.task3.subtask2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ModifiedStringTest {

    @Test
    public void hashCodeShouldReturnStringLength() {
        ModifiedString modifiedString = new ModifiedString("some string", Util.returnStringLength);

        int expected = 11;
        int actual = modifiedString.hashCode();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void hashCodeShouldReturnSumOfFirstFourChars() {
        ModifiedString modifiedString2 = new ModifiedString("some string", Util.returnSumOfFirstFourChars);

        int expected = 's' + 'o' + 'm' + 'e';
        int actual = modifiedString2.hashCode();

        Assertions.assertEquals(expected, actual);
    }
}

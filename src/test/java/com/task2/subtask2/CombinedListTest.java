package com.task2.subtask2;

import com.task1.subtask2.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class CombinedListTest {
    List<String> list;


    @BeforeEach
    void setUp() {
        List<String> unmodifiableList = new ArrayList<>(3);
        unmodifiableList.add("a");
        unmodifiableList.add(null);
        unmodifiableList.add("b");
        list = new CombinedList<>(unmodifiableList);
    }

    @Test
    void shouldReturnListSize() {
        list.add("c");

        Assertions.assertEquals(4, list.size());
    }

    @Test
    void shouldAddElementToEndOfListWhenAddMethodCalled() {
        list.add("c");
        list.add("d");

        int indexOfLastElement = list.size() - 1;
        int indexOfElementBeforeLast = list.size() - 2;
        boolean check = list.get(indexOfLastElement).equals("d") && list.get(indexOfElementBeforeLast).equals("c") && list.size() == 5;

        Assertions.assertTrue(check);
    }

    @Test
    void shouldAddElementAtSpecifiedPositionWhenAddMethodCalled() {
        list.add(3, "c");
        list.add(3, "d");

        boolean check = list.get(3).equals("d") && list.get(4).equals("c") && list.size() == 5;

        Assertions.assertTrue(check);
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenAddElementToUnmodifiableList() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add(2, "a"));
    }


    @Test
    void shouldRemoveElementByIndexFromModifiableListWhenRemoveMethodCalled() {
        list.add(3, "c");

        boolean check = list.remove(3).equals("c");

        Assertions.assertTrue(check);
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenRemoveFromUnmodifiableList() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.remove(1));
    }

    @Test
    void shouldRemoveElementWhenRemoveMethodCalled() {
        list.add("c");

        boolean check = list.remove("c") && list.size() == 3;

        Assertions.assertTrue(check);
    }

    @Test
    void shouldReturnFalseWhenObjectToRemoveDoesNotExistInModifiableList() {
        boolean check = !list.remove("c") && list.size() == 3;

        Assertions.assertTrue(check);
    }

    @Test
    void shouldReturnElementFromListWhenGetMethodCalled() {
        String expected = null;
        String actual = list.get(1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionWhenGetMethodCalled() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }


    @Test
    void shouldReturnFollowingElementWhenNextMethodCalled() {
        list.add(null);
        list.add("c");
        Iterator<String> iterator = list.iterator();

        boolean check = iterator.next().equals("a")
                && iterator.next() == null
                && iterator.next().equals("b")
                && iterator.next() == null
                && iterator.next().equals("c")
                && !iterator.hasNext();

        Assertions.assertTrue(check);
    }

    @Test
    void shouldThrowNoSuchElementExceptionWhenNextMethodCalled() {
        Iterator<String> iterator = list.iterator();

        iterator.next();
        iterator.next();
        iterator.next();

        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }
}
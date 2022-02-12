package com.task1.subtask2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

class MyListTest {
    MyList<String> list = new MyList<>();


    @BeforeEach
    void setUp() {
        list = new MyList<>(10);
        list.add("a");
        list.add(null);
        list.add("b");
    }

    @Test
    void addToEnd() {
        list.add("c");
        list.add("d");
        int indexOfLastElement = list.size() - 1;
        int indexOfElementBeforeLast = list.size() - 2;
        boolean check = list.get(indexOfLastElement).equals("d") && list.get(indexOfElementBeforeLast).equals("c") && list.size() == 5;

        Assertions.assertTrue(check);
    }

    @Test
    void AddToPosition() {
        list.add(1, "c");
        list.add(1, "d");

        boolean check = list.get(1).equals("d") && list.get(2).equals("c") && list.size() == 5;
        Assertions.assertTrue(check);
    }

    @Test
    void AddToPositionWhenIndexOutOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.add(-40, "a"));
    }


    @Test
    void removeByIndex() {
        boolean check = list.remove(1) == null && list.size() == 2;
        Assertions.assertTrue(check);
    }

    @Test
    void removeByIndexWhenIndexOutOfBounds() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }

    @Test
    void RemoveObjectWhenObjectExists() {
        boolean check = list.remove(null) && list.remove("b") && list.size() == 1;
        Assertions.assertTrue(check);
    }

    @Test
    void RemoveObjectWhenObjectDoesNotExist() {
        boolean check = !list.remove("null") && list.size() == 3;
        Assertions.assertTrue(check);
    }

    @Test
    void getElementByIndex() {
        String expected = null;
        String actual = list.get(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getElementByIndexWhenIndexDoesNotExist() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }


    @Test
    void iterator() {
        Iterator<String> iterator = list.iterator((a) -> a != null);
        boolean check = iterator.next().equals("a") && iterator.next().equals("b") && !iterator.hasNext();

        Assertions.assertTrue(check);
    }

    @Test
    void iteratorDoesNotHaveNext() {
        Iterator<String> iterator = list.iterator((a) -> a != null);
        iterator.next();
        iterator.next();

        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

}
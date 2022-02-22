package com.task2.subtask1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

class CopyOnWriteListTest {
    private List<String> list;


    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledIfElementWasAdded() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        Iterator<String> iterator = list.iterator();
        list.add("c");
        iterator.next();
        assertEquals(3, list.size());
    }

    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledIfElementWasRemoved() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        Iterator<String> iterator = list.iterator();
        list.remove("a");
        iterator.next();
        assertEquals(1, list.size());
    }

    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledIfElementWasAddedByIndex() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        Iterator<String> iterator = list.iterator();
        list.add(0, "c");
        iterator.next();
        assertEquals(3, list.size());
    }

    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledIfElementWasRemovedByIndex() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        Iterator<String> iterator = list.iterator();
        list.remove(1);
        iterator.next();
        assertEquals(1, list.size());
    }

    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledAfterRemoveAllMethod() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> elementsToRemove = new ArrayList<>();
        elementsToRemove.add("a");
        elementsToRemove.add("x");
        Iterator<String> iterator = list.iterator();
        list.removeAll(elementsToRemove);
        iterator.next();
        assertEquals(2, list.size());
    }

    @Test
    public void shouldNotThrowConcurrentModificationExceptionWhenNextMethodCalledAfterRetainAllMethod() {
        list = new CopyOnWriteList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        List<String> elementsToSave = new ArrayList<>();
        elementsToSave.add("a");
        elementsToSave.add("x");
        Iterator<String> iterator = list.iterator();
        list.retainAll(elementsToSave);
        iterator.next();
        assertEquals(1, list.size());
    }

}
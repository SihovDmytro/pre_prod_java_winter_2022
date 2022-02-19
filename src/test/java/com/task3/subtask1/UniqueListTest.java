package com.task3.subtask1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniqueListTest {
    private List<String> list = new UniqueList<>();

    @BeforeEach
    void setUp() {
        list.add("a");
        list.add("b");
        list.add("c");
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenSetMethodChangesElementToExisting() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.set(0, "b"));
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenAddExistingElement() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add("b"));
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenAtLeastOneElementToAddAlreadyExists() {
        List<String> collection = new ArrayList<>();
        collection.add("1");
        collection.add("2");
        collection.add("a");

        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.addAll(collection));
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenCollectionToAddHasEqualElements() {
        List<String> collection = new ArrayList<>();
        collection.add("1");
        collection.add("2");
        collection.add("1");

        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.addAll(collection));
    }

    @Test
    void shouldThrowUnsupportedOperationExceptionWhenModifiedElementAlreadyExists() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.replaceAll((element) -> element = "b" ));
    }
}
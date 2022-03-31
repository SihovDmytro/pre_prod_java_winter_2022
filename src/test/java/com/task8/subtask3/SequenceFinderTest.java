package com.task8.subtask3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SequenceFinderTest {
    private static ExecutorService service = Executors.newFixedThreadPool(5);
    private static final String TEST_TEXT = "abrakadabra dddddddddd brabrabrabra";

    @AfterAll
    static void afterAll() {
        service.shutdown();
    }

    @Test
    public void shouldReturnLongestSequenceStartingAtIndex() throws ExecutionException, InterruptedException {
        Future<String> future = service.submit(new SequenceFinder(12, TEST_TEXT));

        String expected = "ddddd";
        String actual = future.get();

        Assertions.assertEquals(expected, actual);
    }
}
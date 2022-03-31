package com.task8;

import com.task8.subtask1.PrimeFinderUtil;
import com.task8.subtask2.ExecutorsUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

class PrimeFinderTest {

    @Test
    public void shouldFindPrimeNumbersUsingDifferentWays() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        List<Integer> generalCollection = PrimeFinderUtil.getPrimeInRangeGeneralCollection(2, 100000, 5);
        System.out.println("generalCollection: " + (System.currentTimeMillis() - start) + " mills");
        start = System.currentTimeMillis();
        List<Integer> separateCollections = PrimeFinderUtil.getPrimeInRangeSeparateCollections(2, 100000, 5);
        System.out.println("separateCollections: " + (System.currentTimeMillis() - start) + " mills");
        start = System.currentTimeMillis();
        List<Integer> executors = ExecutorsUtil.getPrimeInRangeExecutors(2, 100000, 5);
        System.out.println("executors: " + (System.currentTimeMillis() - start) + " mills");

        boolean check = generalCollection.equals(separateCollections) && generalCollection.equals(executors);

        Assertions.assertTrue(check);
    }

}
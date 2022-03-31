package com.task8.subtask2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsUtil {
    public static List<Integer> getPrimeInRangeExecutors(int start, int end, int numberOfThreads) throws InterruptedException, ExecutionException {
        List<Integer> primeNumbers = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<List<Integer>>> futureList = new ArrayList<>(numberOfThreads);
        int numbersPerThread = (end - start) / numberOfThreads;
        int restNumbers = start + numbersPerThread + (end - start) % numberOfThreads;
        futureList.add(service.submit(new PrimeFinderCallable(start, restNumbers)));
        start = restNumbers;

        for (int i = 1; i < numberOfThreads; i++) {
            futureList.add(service.submit(new PrimeFinderCallable(start, start + numbersPerThread)));
            start += numbersPerThread;
        }

        for (Future<List<Integer>> future : futureList) {
            primeNumbers.addAll(future.get());
        }

        Collections.sort(primeNumbers);
        service.shutdown();
        return primeNumbers;
    }
}

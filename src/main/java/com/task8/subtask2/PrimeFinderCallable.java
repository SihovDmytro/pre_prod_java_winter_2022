package com.task8.subtask2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PrimeFinderCallable implements Callable<List<Integer>> {
    private int start;
    private int end;

    public PrimeFinderCallable(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public List<Integer> call() {
        List<Integer> primeNumbers = new ArrayList<>();

        for (int i = start; i < end; i++) {
            boolean isPrime = true;
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                primeNumbers.add(i);
            }
        }

        return primeNumbers;
    }
}

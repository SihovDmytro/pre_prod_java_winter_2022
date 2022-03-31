package com.task8.subtask1;

import java.util.List;

public class PrimeFinderThread extends Thread {
    private int start;
    private int end;
    private List<Integer> primeNumbers;

    public PrimeFinderThread(int start, int end, List<Integer> primeNumbers) {
        this.start = start;
        this.end = end;
        this.primeNumbers = primeNumbers;
    }

    public List<Integer> getPrimeNumbers() {
        return primeNumbers;
    }

    @Override
    public void run() {
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
    }
}

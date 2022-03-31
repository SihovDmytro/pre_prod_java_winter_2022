package com.task8.subtask1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PrimeFinderUtil {

    public static void printPrimeNumbers(List<Integer> primeNumbers) {
        System.out.print("Prime numbers: ");

        for (int number : primeNumbers) {
            System.out.print(number + " ");
        }

        System.out.println();
    }

    public static List<Integer> getPrimeInRangeSeparateCollections(int start, int end, int numberOfThreads) throws InterruptedException {
        List<List<Integer>> allContainers = new ArrayList<>(numberOfThreads);
        PrimeFinderThread[] threads = new PrimeFinderThread[numberOfThreads];
        int numbersPerThread = (end - start) / numberOfThreads;
        int restNumbers = start + numbersPerThread + (end - start) % numberOfThreads;
        (threads[0] = new PrimeFinderThread(start,
                restNumbers,
                new ArrayList<>())).start();
        start = restNumbers;
        allContainers.add(threads[0].getPrimeNumbers());

        for (int i = 1; i < numberOfThreads; i++) {
            (threads[i] = new PrimeFinderThread(start, start + numbersPerThread, new ArrayList<>())).start();
            start += numbersPerThread;
            allContainers.add(threads[i].getPrimeNumbers());
        }

        for (int i = 0; i < numberOfThreads; i++) {

            threads[i].join();
        }

        List<Integer> primeNumbers = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            primeNumbers.addAll(allContainers.get(i));
        }

        Collections.sort(primeNumbers);
        return primeNumbers;
    }

    public static List<Integer> getPrimeInRangeGeneralCollection(int start, int end, int numberOfThreads) throws InterruptedException {
        List<Integer> primeNumbers = new CopyOnWriteArrayList<>();
        PrimeFinderThread[] threads = new PrimeFinderThread[numberOfThreads];

        int numbersPerThread = (end - start) / numberOfThreads;
        int restNumbers = start + numbersPerThread + (end - start) % numberOfThreads;
        (threads[0] = new PrimeFinderThread(start,
                restNumbers,
                primeNumbers)).start();
        start = restNumbers;

        for (int i = 1; i < numberOfThreads; i++) {
            (threads[i] = new PrimeFinderThread(start, start + numbersPerThread, primeNumbers)).start();
            start += numbersPerThread;
        }

        for (int i = 0; i < numberOfThreads; i++) {

            threads[i].join();
        }

        Collections.sort(primeNumbers);
        return primeNumbers;
    }
}

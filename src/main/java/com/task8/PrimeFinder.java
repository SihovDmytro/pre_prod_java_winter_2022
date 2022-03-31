package com.task8;

import com.task8.subtask1.PrimeFinderUtil;
import com.task8.subtask2.ExecutorsUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class PrimeFinder {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continueWork = true;
        while (continueWork) {
            try {
                System.out.println("Enter start number: ");
                int startNumber = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter end number: ");
                int endNumber = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter number of threads: ");
                int numberOfThreads = Integer.parseInt(scanner.nextLine());
                if (startNumber < 2) {
                    startNumber = 2;
                }
                System.out.println("Using separate collections for each thread");
                PrimeFinderUtil.printPrimeNumbers(PrimeFinderUtil.getPrimeInRangeSeparateCollections(startNumber, endNumber, numberOfThreads));
                System.out.println("Using general collection for all threads");
                PrimeFinderUtil.printPrimeNumbers(PrimeFinderUtil.getPrimeInRangeGeneralCollection(startNumber, endNumber, numberOfThreads));
                System.out.println("Implementation with Executors");
                PrimeFinderUtil.printPrimeNumbers(ExecutorsUtil.getPrimeInRangeExecutors(startNumber, endNumber, numberOfThreads));
            } catch (NumberFormatException | InterruptedException | ExecutionException exception) {
                System.out.println("Cannot find prime numbers");
            }
            System.out.println("enter '1' to continue");
            if (!scanner.nextLine().equals("1")) {
                continueWork = false;
            }
        }
    }
}

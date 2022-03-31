package com.task8.subtask3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {

    private static Scanner scanner = new Scanner(System.in);
    private static ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        boolean continueWork = true;
        while (continueWork) {
            System.out.println("Enter filename:");
            String filename = scanner.nextLine();
            File file = new File(filename);
            if (!file.exists() || !file.isFile()) {
                System.out.println("File doesn't exist");
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] bytes = Files.readAllBytes(file.toPath());
                if (bytes.length > 0) {
                    searchLongestSequence(bytes);
                }
            } catch (IOException | ExecutionException | NullPointerException exception) {
                System.out.println("Cannot find max byte sequence");
            }
            System.out.println("enter '1' to continue");
            if (!scanner.nextLine().equals("1")) {
                continueWork = false;
            }
        }
        service.shutdown();
        scanner.close();
    }

    private static void searchLongestSequence(byte[] bytes) throws ExecutionException, InterruptedException {
        String text = new String(bytes);
        String longestSequence = "";

        for (int i = 0; i < text.length(); i++) {
            Future<String> future = service.submit(new SequenceFinder(i, text));
            String sequence = future.get();
            if (sequence.length() > longestSequence.length()) {
                System.out.printf("New longest sequence found: %s | length: %d%n", sequence, sequence.length());
                longestSequence = sequence;
            }
        }

        if (longestSequence.equals("")) {
            throw new NullPointerException();
        }
        System.out.printf(
                "======================%nLongest sequence: %s%nLength: %d%nfirst occurrence: %d%nsecond occurrence: %d%n",
                longestSequence,
                longestSequence.length(),
                text.indexOf(longestSequence),
                SequenceFinderUtil.findSecondOccurrence(text, longestSequence));
    }
}

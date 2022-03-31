package com.task8.subtask3;

public class SequenceFinderUtil {

    public static int findSecondOccurrence(String text, String sequence) {
        return text.indexOf(sequence, text.indexOf(sequence) + sequence.length());
    }
}

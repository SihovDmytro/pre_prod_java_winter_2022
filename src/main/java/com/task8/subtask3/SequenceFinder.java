package com.task8.subtask3;

import java.util.concurrent.Callable;

public class SequenceFinder implements Callable<String> {
    private int index;
    private String text;

    public SequenceFinder(int index, String text) {
        this.index = index;
        this.text = text;
    }

    @Override
    public String call() {
        String longestSequence = "";

        for (int j = index; j < text.length(); j++) {
            String currSequence = text.substring(index, j + 1);
            int secondIndex = SequenceFinderUtil.findSecondOccurrence(text, currSequence);
            if (secondIndex != -1 && currSequence.length() > longestSequence.length()) {
                longestSequence = currSequence;
            } else {
                break;
            }
        }

        return longestSequence;
    }
}

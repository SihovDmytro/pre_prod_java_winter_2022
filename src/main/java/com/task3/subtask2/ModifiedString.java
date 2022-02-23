package com.task3.subtask2;

import java.util.function.Function;

public class ModifiedString {
    private String text;
    private final Function<String, Integer> selectedAlgorithm;

    public ModifiedString(String text, Function<String, Integer> algorithm) {
        this.text = text;
        selectedAlgorithm = algorithm;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ModifiedString{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return selectedAlgorithm.apply(text);
    }
}

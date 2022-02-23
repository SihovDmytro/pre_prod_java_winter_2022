package com.task3.subtask2;

import java.util.function.Function;

public class ModifiedString {
    private String text;
    private boolean useFirstAlgorithm = true;
    private static final Function<String, Integer> algorithm1 = (String::length);
    private static final Function<String, Integer> algorithm2 = ((txt) -> {
        int hash = 0;
        for (int i = 0; i < 4 && i < txt.length(); i++) {
            hash += txt.charAt(i);
        }
        return hash;
    });

    public ModifiedString(String text) {
        this.text = text;
    }

    public ModifiedString(String text, boolean useFirstAlgorithm) {
        this.text = text;
        this.useFirstAlgorithm = useFirstAlgorithm;
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

    public void switchAlgorithm() {
        useFirstAlgorithm = !useFirstAlgorithm;
    }

    @Override
    public int hashCode() {
        if(useFirstAlgorithm)
        {
            return algorithm1.apply(text);
        }
        return algorithm2.apply(text);
    }
}

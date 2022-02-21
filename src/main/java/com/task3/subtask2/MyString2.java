package com.task3.subtask2;

import java.util.Objects;

public class MyString2 implements MyString {
    String text;

    public MyString2(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MyString2{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyString2 myString = (MyString2) o;

        return Objects.equals(text, myString.text);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < 4 && i < text.length(); i++) {
            hash += text.charAt(i);
        }
        return hash;
    }
}

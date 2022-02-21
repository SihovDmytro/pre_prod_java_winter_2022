package com.task3.subtask2;

import java.util.Objects;

public class MyString1 implements MyString {
    String text;

    public MyString1(String text) {
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
        return "MyString1{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyString1 myString = (MyString1) o;

        return Objects.equals(text, myString.text);
    }

    @Override
    public int hashCode() {
        return text.length();
    }
}

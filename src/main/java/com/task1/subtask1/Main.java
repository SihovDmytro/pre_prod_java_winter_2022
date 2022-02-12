package com.task1.subtask1;

import com.task1.subtask2.MyList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        MyList<String> list = new MyList<>(1);
        list.add("a");
        list.add("b");
        list.add(null);
        list.add("d");
        list.add("e");

    }
}

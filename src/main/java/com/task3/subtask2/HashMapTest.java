package com.task3.subtask2;

import com.task1.subtask1.CannedFood;
import com.task1.subtask1.Food;
import com.task1.subtask1.Product;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        System.out.println("============================================hashCode = length of string. HashMap");
        HashMap<MyString, Product> hashMap = new HashMap<>();
        hashMap.put(new MyString1("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new MyString1("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new MyString1("car"), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new MyString1("table"), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new MyString1("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new MyString1("chair"), new Product(new BigDecimal(5001), "chair1"));
        for (Map.Entry<MyString, Product> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
        System.out.println("============================================hashCode = length of string. LinkedHashMap");
        LinkedHashMap<MyString, Product> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new MyString1("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new MyString1("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new MyString1("car"), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new MyString1("table"), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new MyString1("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new MyString1("chair"), new Product(new BigDecimal(5001), "chair1"));
        for (Map.Entry<MyString, Product> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
        System.out.println("============================================hashCode = sum of first four chars. HashMap");
        HashMap<MyString, Product> hashMap2 = new HashMap<>();
        hashMap2.put(new MyString2("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap2.put(new MyString2("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap2.put(new MyString2("car"), new Product(new BigDecimal(1000000), "car1"));
        hashMap2.put(new MyString2("table"), new Product(new BigDecimal(5000), "table1"));
        hashMap2.put(new MyString2("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap2.put(new MyString2("chair"), new Product(new BigDecimal(5001), "chair1"));
        for (Map.Entry<MyString, Product> entry : hashMap2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
        System.out.println("============================================hashCode = sum of first four chars. LinkedHashMap");
        LinkedHashMap<MyString, Product> linkedHashMap2 = new LinkedHashMap<>();
        linkedHashMap2.put(new MyString2("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap2.put(new MyString2("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap2.put(new MyString2("car"), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap2.put(new MyString2("table"), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap2.put(new MyString2("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap2.put(new MyString2("chair"), new Product(new BigDecimal(5001), "chair1"));
        for (Map.Entry<MyString, Product> entry : linkedHashMap2.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }
}

package com.task3.subtask2;

import com.task1.subtask1.CannedFood;
import com.task1.subtask1.Food;
import com.task1.subtask1.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HashMapTest {
    private HashMap<ModifiedString, Product> hashMap;
    private LinkedHashMap<ModifiedString, Product> linkedHashMap;


    @Test
    public void shouldPrintHashMapUsingFirstAlgorithm() {
        hashMap = new HashMap<>();
        hashMap.put(new ModifiedString("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("car"), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new ModifiedString("table"), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new ModifiedString("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new ModifiedString("chair"), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintHashMapUsingFirstAlgorithm");
        for (Map.Entry<ModifiedString, Product> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintLinkedHashMapUsingFirstAlgorithm() {
        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new ModifiedString("apple"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("apple123"), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("car"), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new ModifiedString("table"), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new ModifiedString("canned beans"), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new ModifiedString("chair"), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintLinkedHashMapUsingFirstAlgorithm");
        for (Map.Entry<ModifiedString, Product> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintHashMapUsingSecondAlgorithm() {
        hashMap = new HashMap<>();
        hashMap.put(new ModifiedString("apple", false), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("apple123", false), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("car", false), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new ModifiedString("table", false), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new ModifiedString("canned beans", false), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new ModifiedString("chair", false), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintHashMapUsingSecondAlgorithm");
        for (Map.Entry<ModifiedString, Product> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintLinkedHashMapUsingSecondAlgorithm() {
        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new ModifiedString("apple", false), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("apple123", false), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("car", false), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new ModifiedString("table", false), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new ModifiedString("canned beans", false), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new ModifiedString("chair", false), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintLinkedHashMapUsingSecondAlgorithm");
        for (Map.Entry<ModifiedString, Product> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }
}

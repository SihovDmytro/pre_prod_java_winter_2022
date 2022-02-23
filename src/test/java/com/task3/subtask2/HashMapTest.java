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
    public void shouldPrintHashMapWhenHashCodeMethodReturnsStringLength() {
        hashMap = new HashMap<>();
        hashMap.put(new ModifiedString("apple", Util.returnStringLength), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("apple123", Util.returnStringLength), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("car", Util.returnStringLength), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new ModifiedString("table", Util.returnStringLength), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new ModifiedString("canned beans", Util.returnStringLength), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new ModifiedString("chair", Util.returnStringLength), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintHashMapWhenHashCodeMethodReturnsStringLength");
        for (Map.Entry<ModifiedString, Product> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintLinkedHashMapWhenHashCodeMethodReturnsStringLength() {
        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new ModifiedString("apple", Util.returnStringLength), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("apple123", Util.returnStringLength), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("car", Util.returnStringLength), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new ModifiedString("table", Util.returnStringLength), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new ModifiedString("canned beans", Util.returnStringLength), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new ModifiedString("chair", Util.returnStringLength), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintLinkedHashMapWhenHashCodeMethodReturnsStringLength");
        for (Map.Entry<ModifiedString, Product> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars() {
        hashMap = new HashMap<>();
        hashMap.put(new ModifiedString("apple", Util.returnSumOfFirstFourChars), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("apple123", Util.returnSumOfFirstFourChars), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("car", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new ModifiedString("table", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new ModifiedString("canned beans", Util.returnSumOfFirstFourChars), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new ModifiedString("chair", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars");
        for (Map.Entry<ModifiedString, Product> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    @Test
    public void shouldPrintLinkedHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars() {
        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new ModifiedString("apple", Util.returnSumOfFirstFourChars), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("apple123", Util.returnSumOfFirstFourChars), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("car", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new ModifiedString("table", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new ModifiedString("canned beans", Util.returnSumOfFirstFourChars), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new ModifiedString("chair", Util.returnSumOfFirstFourChars), new Product(new BigDecimal(5001), "chair1"));

        System.out.println("shouldPrintLinkedHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars");
        for (Map.Entry<ModifiedString, Product> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }
}

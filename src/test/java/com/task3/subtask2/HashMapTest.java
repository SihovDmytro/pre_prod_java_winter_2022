package com.task3.subtask2;

import com.shop.entity.CannedFood;
import com.shop.entity.Food;
import com.shop.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class HashMapTest {
    private HashMap<ModifiedString, Product> hashMap;
    private LinkedHashMap<ModifiedString, Product> linkedHashMap;


    @Test
    public void shouldPrintHashMapWhenHashCodeMethodReturnsStringLength() {
        fillHashMap(Util.returnStringLength);
        System.out.println("shouldPrintHashMapWhenHashCodeMethodReturnsStringLength");
        printMap(hashMap);
    }

    @Test
    public void shouldPrintLinkedHashMapWhenHashCodeMethodReturnsStringLength() {
        fillLinkedHashMap(Util.returnStringLength);
        System.out.println("shouldPrintLinkedHashMapWhenHashCodeMethodReturnsStringLength");
        printMap(linkedHashMap);
    }

    @Test
    public void shouldPrintHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars() {
        fillHashMap(Util.returnSumOfFirstFourChars);
        System.out.println("shouldPrintHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars");
        printMap(hashMap);
    }

    @Test
    public void shouldPrintLinkedHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars() {
        fillLinkedHashMap(Util.returnSumOfFirstFourChars);
        System.out.println("shouldPrintHashMapWhenHashCodeMethodReturnsSumOfFirstFourChars");
        printMap(linkedHashMap);
    }

    private void printMap(HashMap<ModifiedString, Product> map) {
        for (Map.Entry<ModifiedString, Product> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getKey().hashCode());
        }
    }

    private void fillHashMap(Function<String, Integer> algorithm) {
        hashMap = new HashMap<>();
        hashMap.put(new ModifiedString("apple", algorithm), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("apple123", algorithm), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        hashMap.put(new ModifiedString("car", algorithm), new Product(new BigDecimal(1000000), "car1"));
        hashMap.put(new ModifiedString("table", algorithm), new Product(new BigDecimal(5000), "table1"));
        hashMap.put(new ModifiedString("canned beans", algorithm), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        hashMap.put(new ModifiedString("chair", algorithm), new Product(new BigDecimal(5001), "chair1"));
    }

    private void fillLinkedHashMap(Function<String, Integer> algorithm) {
        linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(new ModifiedString("apple", algorithm), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("apple123", algorithm), new Food(new BigDecimal(12), "apple1", 10, 500, Calendar.getInstance()));
        linkedHashMap.put(new ModifiedString("car", algorithm), new Product(new BigDecimal(1000000), "car1"));
        linkedHashMap.put(new ModifiedString("table", algorithm), new Product(new BigDecimal(5000), "table1"));
        linkedHashMap.put(new ModifiedString("canned beans", algorithm), new CannedFood(new BigDecimal(50), "beans1", 100, 2000, Calendar.getInstance(), 100, "dasdadvs"));
        linkedHashMap.put(new ModifiedString("chair", algorithm), new Product(new BigDecimal(5001), "chair1"));
    }
}

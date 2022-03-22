package com.shop.util;


import com.shop.entity.Furniture;
import com.shop.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class SerializerTest {
    private static final String serializePath = "src/test/java/com/shop/util/serializeData.dat";
    private static final String gzipSerializePath = "src/test/java/com/shop/util/gzipSerializeData.dat";

    @BeforeEach
    void setUp() {
        File file = new File(serializePath);
        if (file.exists()) {
            file.delete();
        }
        File gzipFile = new File(gzipSerializePath);
        if (gzipFile.exists()) {
            gzipFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        setUp();
    }

    @Test
    public void shouldSerializeObjectToFile() {
        List<Product> products = new ArrayList<>();
        products.add(new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140));
        products.add(new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198));
        Serializer.serializeProducts(products, 1, serializePath);

        boolean check = new File(serializePath).exists();

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldDeserializeObjectFromFile() {
        List<Product> expected = new ArrayList<>();
        expected.add(new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140));
        expected.add(new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198));
        Serializer.serializeProducts(expected, 1, serializePath);

        List<Product> actual = Serializer.deserializeProducts(serializePath);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSerializeObjectUsingGzip() {
        List<Product> products = new ArrayList<>();
        products.add(new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140));
        products.add(new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198));
        Serializer.serializeProductsGZIP(products, gzipSerializePath);
        Serializer.serializeProducts(products, 1, serializePath);

        long simpleSerialize = new File(serializePath).length();
        long gzipSerialize = new File(gzipSerializePath).length();
        boolean check = simpleSerialize > gzipSerialize;

        System.out.println("simple serialize: " + simpleSerialize +
                "\ngzip serialize: " + gzipSerialize);
        Assertions.assertTrue(check);
    }
}
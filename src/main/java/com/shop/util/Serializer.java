package com.shop.util;

import com.shop.entity.CannedFood;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class Serializer {
    private static final String PRODUCTS_FILE = ShopProperties.getProperty("products.file");
    private static final Logger LOG = LogManager.getLogger(Serializer.class);

    private static List<Product> getDefaultProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140));
        productList.add(new Furniture(new BigDecimal(4055), "dinner table", 45, 102, 200));
        productList.add(new Furniture(new BigDecimal(1200), "double bad", 22, 140, 200));
        productList.add(new Furniture(new BigDecimal(2499), "single bad", 30, 95, 200));
        productList.add(new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 5);
        productList.add(new CannedFood(new BigDecimal("46.5"), "canned beans", 80, 400, calendar, 10, "KharkivFactory"));
        productList.add(new CannedFood(new BigDecimal("70"), "canned salmon", 200, 500, calendar, 15, "KharkivFactory"));
        calendar.add(Calendar.YEAR, -2);
        productList.add(new CannedFood(new BigDecimal("55"), "canned pineapple", 122, 350, calendar, 19, "KharkivFactory"));
        return productList;
    }

    public static void serializeProducts(List<Product> products) {
        serializeProducts(products, 1, PRODUCTS_FILE);
    }

    public static void serializeProducts(List<Product> products, int number, String path) {
        LOG.trace("serializeProducts start");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            for (int i = 0; i < number; i++) {
                objectOutputStream.writeObject(products);
            }
        } catch (IOException exception) {
            LOG.error("Cannot serialize", exception);
        }
        LOG.trace("serializeProducts end");
    }

    public static List<Product> deserializeProducts() {
        return deserializeProducts(PRODUCTS_FILE);
    }

    public static List<Product> deserializeProducts(String path) {
        List<Product> products = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path))) {
            products = (List<Product>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            LOG.error("Cannot deserialize", exception);
            products = getDefaultProducts();
        }
        return products;
    }

    public static void serializeProductsGZIP(List<Product> products, String path) {
        LOG.trace("serializeProductsGZIP start");
        try (FileOutputStream fos = new FileOutputStream(path);
             GZIPOutputStream gos = new GZIPOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(gos)) {
            oos.writeObject(products);
            oos.flush();
        } catch (IOException exception) {
            LOG.error("Cannot serialize", exception);
        }
        LOG.trace("serializeProductsGZIP end");
    }
}

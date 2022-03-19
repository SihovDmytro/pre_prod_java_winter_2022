package com.shop.util;

import com.shop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class Serializer {
    private static final String PRODUCTS_FILE = ShopProperties.getProperty("products.file");
    private static final Logger LOG = LogManager.getLogger(Serializer.class);

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

package com.task1.subtask1;

import com.task4.Path;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class Product {
    BigDecimal price;
    String name;

    public Product() {
    }

    public Product(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!price.equals(product.price)) return false;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        int result = price.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        Properties properties = new Properties();
        try (InputStream input = Product.class.getClassLoader().getResourceAsStream(Path.pathToProperties)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "name: " + name + "\n" +
                "price for one item: " + price + " " + properties.getProperty("product.currency") + "\n";
    }
}

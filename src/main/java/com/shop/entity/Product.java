package com.shop.entity;

import com.shop.util.ShopProperties;

import java.math.BigDecimal;

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
        return "name: " + name + "\n" +
                "price for one item: " + price + " " + ShopProperties.getProperty("product.currency") + "\n";
    }
}
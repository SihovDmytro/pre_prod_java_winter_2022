package com.shop.dao.entity;

import com.shop.dao.entity.annotation.FieldToInput;
import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Scanner;

public class Product implements Serializable, ProductInput {
    @FieldToInput(fieldName = "product.price")
    private BigDecimal price;
    @FieldToInput(fieldName = "product.name")
    private String name;
    private static final Logger LOG = LogManager.getLogger(Product.class);

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


    @Override
    public boolean consoleInput(Scanner scanner) {
        try {
            System.out.println("Enter price: ");
            String priceString = scanner.nextLine();
            LOG.trace("priceString: " + priceString);
            BigDecimal price = new BigDecimal(priceString);
            setPrice(price);
            System.out.println("Enter name: ");
            String name = scanner.nextLine();
            LOG.trace("name: " + name);
            setName(name);
            return true;
        } catch (NumberFormatException exception) {
            LOG.trace("Cannot input product from console");
            return false;
        }
    }

    @Override
    public boolean randomInput(int min, int max) {
        BigDecimal price = new BigDecimal(Util.randomInt(min, max));
        LOG.trace("price: " + price);
        setPrice(price);
        String name = Util.randomString("product", min, max);
        LOG.trace("name: " + name);
        setName(name);
        return true;
    }

}

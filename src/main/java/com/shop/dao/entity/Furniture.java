package com.shop.dao.entity;

import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Scanner;

public class Furniture extends Product {
    private int height;
    private int width;
    private int length;
    private static final Logger LOG = LogManager.getLogger(Furniture.class);

    public Furniture() {
    }

    public Furniture(BigDecimal price, String name, int height, int width, int length) {
        super(price, name);
        this.height = height;
        this.width = width;
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return super.toString() +
                "height: " + height + " " + ShopProperties.getProperty("product.dimension") + "\n" +
                "width: " + width + " " + ShopProperties.getProperty("product.dimension") + "\n" +
                "length: " + length + " " + ShopProperties.getProperty("product.dimension") + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Furniture furniture = (Furniture) o;

        if (height != furniture.height) return false;
        if (width != furniture.width) return false;
        return length == furniture.length;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + height;
        result = 31 * result + width;
        result = 31 * result + length;
        return result;
    }

    @Override
    public boolean consoleInput(Scanner scanner) {
        boolean result = super.consoleInput(scanner);
        if (result) {
            try {
                System.out.println("Enter height: ");
                String heightString = scanner.nextLine();
                LOG.trace("heightString: " + heightString);
                int height = Integer.parseInt(heightString);
                setHeight(height);
                System.out.println("Enter width: ");
                String widthString = scanner.nextLine();
                LOG.trace("widthString: " + widthString);
                int width = Integer.parseInt(widthString);
                setWidth(width);
                System.out.println("Enter length: ");
                String lengthString = scanner.nextLine();
                LOG.trace("lengthString: " + lengthString);
                int length = Integer.parseInt(lengthString);
                setLength(length);
                result = true;
            } catch (NumberFormatException exception) {
                LOG.trace("Cannot input product from console");
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean randomInput(int min, int max) {
        super.randomInput(min, max);
        int height = Util.randomInt(min,max);
        LOG.trace("height: "+height);
        setHeight(height);
        int width = Util.randomInt(min,max);
        LOG.trace("width: "+width);
        setWidth(width);
        int length = Util.randomInt(min,max);
        LOG.trace("length: "+length);
        setLength(length);
        return true;
    }
}

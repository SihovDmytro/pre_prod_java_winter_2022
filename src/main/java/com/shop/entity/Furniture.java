package com.shop.entity;

import com.shop.reflection.annotation.FieldToInput;
import com.shop.util.ShopProperties;

import java.math.BigDecimal;

public class Furniture extends Product {
    @FieldToInput(fieldName = "furniture.height")
    private int height;
    @FieldToInput(fieldName = "furniture.width")
    private int width;
    @FieldToInput(fieldName = "furniture.length")
    private int length;

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
}

package com.task1.subtask1;

import com.task4.Path;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

public class Furniture extends Product{
    private int height;
    private int width;
    private int length;

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
        Properties properties = new Properties();
        try (InputStream input = Product.class.getClassLoader().getResourceAsStream(Path.pathToProperties)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.toString() +
                "height: " + height + " " + properties.getProperty("product.dimension") + "\n" +
                "width: " + width + " " + properties.getProperty("product.dimension") + "\n" +
                "length: " + length + " " + properties.getProperty("product.dimension") + "\n";
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

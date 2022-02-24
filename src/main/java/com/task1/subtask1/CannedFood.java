package com.task1.subtask1;

import com.task4.Path;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Properties;

public class CannedFood extends Food {
    int canWeight;
    String manufacturer;

    public CannedFood() {
    }

    public CannedFood(BigDecimal price, String name, int calories, int weight, Calendar expirationDate, int canWeight, String manufacturer) {
        super(price, name, calories, weight, expirationDate);
        this.canWeight = canWeight;
        this.manufacturer = manufacturer;
    }

    public int getCanWeight() {
        return canWeight;
    }

    public void setCanWeight(int canWeight) {
        this.canWeight = canWeight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CannedFood that = (CannedFood) o;

        if (canWeight != that.canWeight) return false;
        return manufacturer.equals(that.manufacturer);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + canWeight;
        result = 31 * result + manufacturer.hashCode();
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
        return super.toString() +
                "can weight: " + canWeight + " " + properties.getProperty("product.weight") + "\n" +
                "manufacturer: " + manufacturer + "\n";
    }
}

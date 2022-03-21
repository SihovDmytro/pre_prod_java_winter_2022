package com.shop.entity;

import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Calendar;

public class CannedFood extends Food {
    private int canWeight;
    private String manufacturer;
    private static final Logger LOG = LogManager.getLogger(CannedFood.class);

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
        return super.toString() +
                "can weight: " + canWeight + " " + ShopProperties.getProperty("product.weight") + "\n" +
                "manufacturer: " + manufacturer + "\n";
    }


}

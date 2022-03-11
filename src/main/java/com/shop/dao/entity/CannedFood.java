package com.shop.dao.entity;

import com.shop.dao.entity.annotation.FieldToInput;
import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;

public class CannedFood extends Food {
    @FieldToInput(fieldName = "cannedFood.canWeight")
    private int canWeight;
    @FieldToInput(fieldName = "cannedFood.manufacturer")
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

    @Override
    public boolean consoleInput(Scanner scanner) {
        boolean result = super.consoleInput(scanner);
        if (result) {
            try {
                System.out.println("Enter can weight: ");
                String canWeightString = scanner.nextLine();
                LOG.trace("canWeightString: " + canWeightString);
                int canWeight = Integer.parseInt(canWeightString);
                setCanWeight(canWeight);
                System.out.println("Enter manufacturer: ");
                String manufacturer = scanner.nextLine();
                LOG.trace("manufacturer: " + manufacturer);
                setManufacturer(manufacturer);
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
        int canWeight = Util.randomInt(min, max);
        LOG.trace("canWeight: " + canWeight);
        setCanWeight(canWeight);
        String manufacturer = Util.randomString("manufacturer", min, max);
        LOG.trace("manufacturer:" + manufacturer);
        setManufacturer(manufacturer);
        return true;
    }
}

package com.shop.entity;

import com.shop.util.Constants;
import com.shop.util.DateUtil;
import com.shop.util.RandomUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Food extends Product {
    private int calories;
    private int weight;
    private Calendar expirationDate;
    private static final Logger LOG = LogManager.getLogger(Food.class);

    public Food(BigDecimal price, String name, int calories, int weight, Calendar expirationDate) {
        super(price, name);
        this.calories = calories;
        this.weight = weight;
        this.expirationDate = expirationDate;
    }

    public Food() {
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Calendar getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Food food = (Food) o;

        if (calories != food.calories) return false;
        if (weight != food.weight) return false;
        return expirationDate.equals(food.expirationDate);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + calories;
        result = 31 * result + weight;
        result = 31 * result + expirationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                "calories: " + calories + " " + ShopProperties.getProperty("product.calories") + "\n" +
                "weight: " + weight + " " + ShopProperties.getProperty("product.weight") + "\n" +
                "expiration date: " + DateUtil.calendarToStringDate(expirationDate) + "\n";
    }

    @Override
    public Product consoleInput(Scanner scanner) {
        super.consoleInput(scanner);
        while (true) {
            try {
                System.out.println("Enter calories: ");
                String caloriesString = scanner.nextLine();
                LOG.trace("caloriesString: " + caloriesString);
                int calories = Integer.parseInt(caloriesString);
                setCalories(calories);
                System.out.println("Enter weight: ");
                String weightString = scanner.nextLine();
                LOG.trace("weightString: " + weightString);
                int weight = Integer.parseInt(weightString);
                setWeight(weight);
                String dateFormat = ShopProperties.getProperty("date.format");
                System.out.println("Enter expiration date(" + dateFormat + "): ");
                String expirationDateString = scanner.nextLine();
                LOG.trace("expirationDateString: " + expirationDateString);
                Calendar expirationDate = DateUtil.stringToCalendar(expirationDateString, new SimpleDateFormat(dateFormat));
                setExpirationDate(expirationDate);
                return this;
            } catch (NumberFormatException exception) {
                LOG.error("Cannot input product from console ", exception);
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    @Override
    public Product randomInput() {
        super.randomInput();
        int calories = RandomUtil.randomInt();
        LOG.trace("calories: " + calories);
        setCalories(calories);
        int weight = RandomUtil.randomInt();
        LOG.trace("weight: " + weight);
        setWeight(weight);
        Calendar expirationDate = Calendar.getInstance();
        LOG.trace("expirationDate:" + DateUtil.calendarToStringDate(expirationDate));
        setExpirationDate(expirationDate);
        return this;
    }

}

package com.shop.template;

import com.shop.entity.Food;
import com.shop.strategy.Filler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class FoodFillerTemplate extends ProductFillerTemplate {
    private static final Logger LOG = LogManager.getLogger(FoodFillerTemplate.class);

    public FoodFillerTemplate(Filler filler) {
        super(filler);
    }

    @Override
    protected void setOtherFields() {
        System.out.println("calories: ");
        int calories = filler.fillInt();
        LOG.trace("calories: " + calories);
        ((Food) product).setCalories(calories);
        System.out.println("weight: ");
        int weight = filler.fillInt();
        LOG.trace("weight: " + weight);
        ((Food) product).setWeight(weight);
        System.out.println("expiration date: ");
        Calendar expirationDate = filler.fillDate();
        LOG.trace("expirationDate: " + expirationDate);
        ((Food) product).setExpirationDate(expirationDate);
    }

    @Override
    protected void setProduct() {
        product = new Food();
    }

}

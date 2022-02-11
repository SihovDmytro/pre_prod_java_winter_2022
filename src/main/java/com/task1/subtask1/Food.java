package com.task1.subtask1;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Food extends Product{
    int calories;
    int weight;
    Calendar expirationDate;

    public Food(BigDecimal price, String name, int calories, int weight, Calendar expirationDate) {
        super(price,name);
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
        return "Food{" +
                " price=" + price +
                ", name='" + name +
                ", calories=" + calories +
                ", weight=" + weight +
                ", expirationDate=" + new SimpleDateFormat("dd.MM.yyyy").format(expirationDate.getTime()) + '\''+
                '}';
    }
}

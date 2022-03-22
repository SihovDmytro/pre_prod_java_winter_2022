package com.shop.template;

import com.shop.entity.CannedFood;
import com.shop.strategy.Filler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CannedFoodFillerTemplate extends FoodFillerTemplate {
    private static final Logger LOG = LogManager.getLogger(CannedFoodFillerTemplate.class);

    public CannedFoodFillerTemplate(Filler filler) {
        super(filler);
    }

    @Override
    protected void setOtherFields() {
        super.setOtherFields();
        System.out.println("can weight: ");
        int canWeight = filler.fillInt();
        LOG.trace("canWeight: " + canWeight);
        ((CannedFood) product).setCanWeight(canWeight);
        System.out.println("manufacturer: ");
        String manufacturer = filler.fillString();
        LOG.trace("manufacturer: " + manufacturer);
        ((CannedFood) product).setManufacturer(manufacturer);
    }

    @Override
    protected void setProduct() {
        product = new CannedFood();
    }
}

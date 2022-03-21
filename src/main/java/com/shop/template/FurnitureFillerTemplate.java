package com.shop.template;

import com.shop.entity.Furniture;
import com.shop.strategy.Filler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FurnitureFillerTemplate extends ProductFillerTemplate {
    private static final Logger LOG = LogManager.getLogger(FurnitureFillerTemplate.class);

    public FurnitureFillerTemplate(Filler filler) {
        super(filler);
    }

    @Override
    protected void setOtherFields() {
        System.out.println("width: ");
        int width = filler.fillInt();
        LOG.trace("width: " + width);
        ((Furniture) product).setWidth(width);
        System.out.println("length: ");
        int length = filler.fillInt();
        LOG.trace("length: " + length);
        ((Furniture) product).setLength(length);
        System.out.println("height: ");
        int height = filler.fillInt();
        LOG.trace("height: " + height);
        ((Furniture) product).setHeight(height);
    }

    @Override
    protected void setProduct() {
        product = new Furniture();
    }
}

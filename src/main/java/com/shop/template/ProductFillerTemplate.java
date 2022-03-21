package com.shop.template;

import com.shop.entity.Product;
import com.shop.strategy.Filler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public abstract class ProductFillerTemplate {
    protected Filler filler;
    protected Product product;
    private static final Logger LOG = LogManager.getLogger(ProductFillerTemplate.class);

    public ProductFillerTemplate(Filler filler) {
        this.filler = filler;
    }

    public Product fillProduct() {
        setProduct();

        System.out.println("name: ");
        String name = filler.fillString();
        LOG.debug("name: " + name);
        product.setName(name);
        System.out.println("price: ");
        BigDecimal price = filler.fillBigDecimal();
        LOG.debug("price: " + price);
        product.setPrice(price);

        setOtherFields();
        return product;
    }

    protected abstract void setOtherFields();

    protected void setProduct() {
        product = new Product();
    }
}

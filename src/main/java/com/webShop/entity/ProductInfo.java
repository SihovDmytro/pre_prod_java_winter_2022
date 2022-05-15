package com.webShop.entity;

import java.math.BigDecimal;

public final class ProductInfo {
    private final Product product;
    private final int number;
    private final BigDecimal unitPrice;

    public ProductInfo(Product product, int number, BigDecimal unitPrice) {
        this.product = new Product(product);
        this.number = number;
        this.unitPrice = unitPrice;
    }

    public Product getProduct() {
        return new Product(product);
    }

    public int getNumber() {
        return number;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}

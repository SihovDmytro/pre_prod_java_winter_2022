package com.shop;

import com.shop.entity.Product;

public class RandomFiller extends ProductFiller {
    @Override
    public Product fill(Product product) {
        return product.randomInput();
    }
}

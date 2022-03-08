package com.shop.dao;

import com.shop.Runner;
import com.shop.dao.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleInput extends ShopDAO {
    private static final Logger LOG = LogManager.getLogger(ConsoleInput.class);


    @Override
    public boolean addNewProduct(Product product) {
        if (product.consoleInput(Runner.getScanner())) {
            getProductList().add(product);
            LOG.trace("Product was added");
            return true;
        }
        LOG.trace("Cannot add product");
        return false;
    }
}

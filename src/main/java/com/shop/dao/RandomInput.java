package com.shop.dao;

import com.shop.dao.entity.Product;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomInput extends ShopDAO {
    private static final Logger LOG = LogManager.getLogger(RandomInput.class);

    @Override
    public boolean addNewProduct(Product product) {
        boolean result = false;
        try {
            int min = Integer.parseInt(ShopProperties.getProperty("random.minInteger"));
            int max = Integer.parseInt(ShopProperties.getProperty("random.maxInteger"));
            if (product.randomInput(min, max)) {
                getProductList().add(product);
                LOG.trace("Product was added");
                result = true;
            }
        } catch (NumberFormatException exception) {
            LOG.trace("Cannot add product");
        }
        return result;
    }
}

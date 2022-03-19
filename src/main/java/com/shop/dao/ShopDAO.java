package com.shop.dao;

import com.shop.entity.Product;
import com.shop.util.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShopDAO {
    private List<Product> productList;
    private static final Logger LOG = LogManager.getLogger(ShopDAO.class);

    public ShopDAO() {
        productList = Serializer.deserializeProducts();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProduct(int id) {
        Product product = null;
        try {
            product = productList.get(id);
        } catch (IndexOutOfBoundsException exception) {
            LOG.error("This product is not available: " + id, exception);
        }
        return product;
    }

}

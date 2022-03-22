package com.shop.dao.impl;

import com.shop.dao.AssortmentDAO;
import com.shop.entity.Product;
import com.shop.util.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AssortmentDAOImpl implements AssortmentDAO {
    private List<Product> productList;
    private static final Logger LOG = LogManager.getLogger(AssortmentDAOImpl.class);

    public AssortmentDAOImpl() {
        productList = Serializer.deserializeProducts();
    }

    @Override
    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public Product getProduct(int id) {
        Product product = null;
        try {
            product = productList.get(id);
        } catch (IndexOutOfBoundsException exception) {
            LOG.error("This product is not available: " + id, exception);
        }
        return product;
    }

    @Override
    public void addProduct(Product product) {
        productList.add(product);
    }
}

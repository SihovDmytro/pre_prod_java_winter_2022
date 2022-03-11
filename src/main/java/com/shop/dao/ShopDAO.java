package com.shop.dao;

import com.shop.dao.entity.Product;
import com.shop.util.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class ShopDAO {
    private List<Product> productList;
    private static final Logger LOG = LogManager.getLogger(ShopDAO.class);

    public ShopDAO() {
        productList = Serializer.deserializeProducts();
//        productList = new ArrayList<>();
//        productList.add(new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140));
//        productList.add(new Furniture(new BigDecimal(4055), "dinner table", 45, 102, 200));
//        productList.add(new Furniture(new BigDecimal(1200), "double bad", 22, 140, 200));
//        productList.add(new Furniture(new BigDecimal(2499), "single bad", 30, 95, 200));
//        productList.add(new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198));
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.YEAR, 5);
//        productList.add(new CannedFood(new BigDecimal("46.5"), "canned beans", 80, 400, calendar, 10, "KharkivFactory"));
//        productList.add(new CannedFood(new BigDecimal("70"), "canned salmon", 200, 500, calendar, 15, "KharkivFactory"));
//        calendar.add(Calendar.YEAR, -2);
//        productList.add(new CannedFood(new BigDecimal("55"), "canned pineapple", 122, 350, calendar, 19, "KharkivFactory"));
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProduct(int id) {
        Product product = null;
        try {
            product = productList.get(id);
        } catch (IndexOutOfBoundsException exception) {
            LOG.info("This product is not available: " + id);
        }
        return product;
    }

    public abstract boolean addNewProduct(Product product);

    public abstract boolean addNewProductReflection(Product product);

}

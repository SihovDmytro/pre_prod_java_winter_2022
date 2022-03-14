package com.task7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

class ProxyTest {
    private ProductFactory factory = new ProductFactory();

    @Test
    public void shouldInvokeGetMethodInMapWhenGetterInProductClassCalled() {
        ProductMapHandler handler = new ProductMapHandler();
        IProduct product = factory.createProductProxy(handler);
        product.setName("name");

        String expected = "name";
        String actual = product.getName();

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void shouldInvokePutMethodInMapWhenSetterInProductClassCalled() {
        ProductMapHandler handler = new ProductMapHandler();
        IProduct product = factory.createProductProxy(handler);
        product.setName("name");
        product.setPrice(new BigDecimal(10));
        Map<String,Object> map = handler.getMap();

        boolean check = map.get("name").equals("name") && map.get("price").equals(new BigDecimal(10));

        Assertions.assertTrue(check);
    }

    @Test
    public void shouldThrowExceptionWhenSetterCalled() {

        UnmodifiableProductHandler handler = new UnmodifiableProductHandler(new Product());
        IProduct product = factory.createProductProxy(handler);
        Assertions.assertThrows(UnsupportedOperationException.class, ()->product.setName("name"));
    }

}
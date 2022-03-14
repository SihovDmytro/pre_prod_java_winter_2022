package com.task7;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProductFactory {
    public IProduct createProductProxy(InvocationHandler handler) {
        return (IProduct) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IProduct.class}, handler);
    }
}

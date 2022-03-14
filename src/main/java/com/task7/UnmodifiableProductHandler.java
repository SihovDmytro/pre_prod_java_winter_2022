package com.task7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UnmodifiableProductHandler implements InvocationHandler {
    private Product product;
    private static final Logger LOG = LogManager.getLogger(UnmodifiableProductHandler.class);

    public UnmodifiableProductHandler(Product product) {
        this.product = product;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.trace("invoke start");
        LOG.debug("method name: "+method.getName());
        if (method.getName().startsWith("set")) {
            throw new UnsupportedOperationException();
        }
        Object object = method.invoke(product,args);
        LOG.debug("object: "+object);
        LOG.trace("invoke end");
        return object;
    }
}

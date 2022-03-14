package com.task7;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ProductMapHandler implements InvocationHandler {
    private Map<String, Object> map;
    private static final Logger LOG = LogManager.getLogger(ProductMapHandler.class);


    public ProductMapHandler() {
        map = new HashMap<>();
        for (Field field : Product.class.getFields()) {
            map.put(field.getName(), null);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        LOG.trace("invoke start");
        Object object = null;
        LOG.debug("method name: "+method.getName());
        if (method.getName().startsWith("set")) {
            String fieldName = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
            map.put(fieldName, args[0]);
            LOG.debug("fieldName: " + fieldName);
        } else if (method.getName().startsWith("get")) {
            String fieldName = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
            LOG.debug("fieldName: " + fieldName);
            object = map.get(fieldName);
            LOG.trace("object: " + object);
        }
        LOG.trace("invoke end");
        return object;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}

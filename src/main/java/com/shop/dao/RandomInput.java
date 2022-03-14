package com.shop.dao;

import com.shop.dao.entity.Product;
import com.shop.dao.entity.annotation.FieldToInput;
import com.shop.util.Localization;
import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Scanner;

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

    @Override
    public boolean addNewProductReflection(Product product) {
        int min = Integer.parseInt(ShopProperties.getProperty("random.minInteger"));
        int max = Integer.parseInt(ShopProperties.getProperty("random.maxInteger"));
        if (randomInputReflection(product, min, max)) {
            getProductList().add(product);
            LOG.trace("Product was added");
            return true;
        }
        LOG.trace("Cannot add product");
        return false;
    }

    private boolean randomInputReflection(Product product, int min, int max) {
        try {
            Class<?> tempClass = product.getClass();
            while (tempClass != Object.class) {
                LOG.trace("tempClass: " + tempClass);
                for (Field field : tempClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(FieldToInput.class)) {
                        String fieldName = Localization.getResource(field.getAnnotation(FieldToInput.class).fieldName());
                        LOG.trace("fieldName: " + fieldName);
                        String setterName = Util.getSetterName(field.getName());
                        LOG.trace("setterName: " + setterName);
                        Class<?> fieldType = field.getType();
                        LOG.trace("fieldType: " + fieldType);
                        Method method = tempClass.getDeclaredMethod(setterName, fieldType);
                        LOG.trace("method: " + method);
                        Object randomObject = generateRandomObject(fieldType, min, max);
                        method.invoke(product, randomObject);
                    }
                }
                tempClass = tempClass.getSuperclass();
            }
            return true;
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            LOG.trace("Cannot input product from console");
            return false;
        }
    }

    private static Object generateRandomObject(Class<?> cls, int min, int max) {
        Object randomObject = null;
        if (cls.isAssignableFrom(int.class)) {
            randomObject = Util.randomInt(min, max);
        } else if (cls.isAssignableFrom(String.class)) {
            randomObject = Util.randomString("randomString", min, max);
        } else if (cls.isAssignableFrom(BigDecimal.class)) {
            randomObject = new BigDecimal(Util.randomInt(min, max));
        }else if (cls.isAssignableFrom(Calendar.class)) {
            randomObject = Util.randomCalendar();
        }

        return randomObject;
    }
}

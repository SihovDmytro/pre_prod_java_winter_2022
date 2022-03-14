package com.shop.dao;

import com.shop.Runner;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

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

    @Override
    public boolean addNewProductReflection(Product product) {
        if (consoleInputReflection(Runner.getScanner(), product)) {
            getProductList().add(product);
            LOG.trace("Product was added");
            return true;
        }
        LOG.trace("Cannot add product");
        return false;
    }

    private boolean consoleInputReflection(Scanner scanner, Product product) {
        try {
            Class<?> tempClass = product.getClass();
            while (tempClass != Object.class) {
                LOG.trace("tempClass: " + tempClass);
                for (Field field : tempClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(FieldToInput.class)) {
                        String fieldName = Localization.getResource(field.getAnnotation(FieldToInput.class).fieldName());
                        LOG.trace("fieldName: " + fieldName);
                        System.out.println(fieldName + ":");
                        String usersInput = scanner.nextLine();
                        LOG.trace("usersInput: " + usersInput);
                        String setterName = Util.getSetterName(field.getName());
                        LOG.trace("setterName: " + setterName);
                        Class<?> fieldType = field.getType();
                        LOG.trace("fieldType: " + fieldType);
                        Method method = tempClass.getDeclaredMethod(setterName, fieldType);
                        LOG.trace("method: " + method);
                        Object parsedObject = parseObject(usersInput, fieldType);
                        LOG.trace("parsedObject: " + parsedObject);
                        if (parsedObject != null)
                            method.invoke(product, parsedObject);
                        else throw new IllegalArgumentException();
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

    private static Object parseObject(String object, Class<?> cls) {
        Object returnType = null;
        try {
            if (cls.isAssignableFrom(int.class)) {
                returnType = Integer.valueOf(object);
            } else if (cls.isAssignableFrom(double.class)) {
                returnType = Double.valueOf(object);
            } else if (cls.isAssignableFrom(boolean.class)) {
                returnType = Boolean.valueOf(object);
            } else if (cls.isAssignableFrom(float.class)) {
                returnType = Float.valueOf(object);
            } else if (cls.isAssignableFrom(long.class)) {
                returnType = Long.valueOf(object);
            } else if (cls.isAssignableFrom(byte.class)) {
                returnType = Byte.valueOf(object);
            } else if (cls.isAssignableFrom(short.class)) {
                returnType = Short.valueOf(object);
            } else if (cls.isAssignableFrom(Calendar.class)) {
                returnType = Util.stringToCalendar(object, new SimpleDateFormat(ShopProperties.getProperty("date.format")));
            } else returnType = cls.getConstructor(new Class[]{String.class}).newInstance(object);
        } catch (Exception exception) {
            LOG.trace("Cannot parse value");
        }
        return returnType;
    }

}

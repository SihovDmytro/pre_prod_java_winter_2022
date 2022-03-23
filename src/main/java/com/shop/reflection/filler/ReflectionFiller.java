package com.shop.reflection.filler;

import com.shop.entity.Product;
import com.shop.reflection.annotation.FieldToInput;
import com.shop.strategy.Filler;
import com.shop.util.Localization;
import com.shop.util.ReflectionUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;

public class ReflectionFiller {
    private static final Logger LOG = LogManager.getLogger(ReflectionFiller.class);
    private Filler filler;

    public ReflectionFiller(Filler filler) {
        this.filler = filler;
    }

    public Product fillProduct(Product emptyProduct) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> tempClass = emptyProduct.getClass();
        while (tempClass != Object.class) {
            LOG.trace("tempClass: " + tempClass);
            for (Field field : tempClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(FieldToInput.class)) {
                    String fieldName = Localization.getResource(field.getAnnotation(FieldToInput.class).fieldName());
                    System.out.println(fieldName + ":");
                    LOG.trace("fieldName: " + fieldName);
                    String setterName = ReflectionUtil.getSetterName(field.getName());
                    LOG.trace("setterName: " + setterName);
                    Class<?> fieldType = field.getType();
                    LOG.trace("fieldType: " + fieldType);
                    Method method = tempClass.getDeclaredMethod(setterName, fieldType);
                    LOG.trace("method: " + method);
                    Object object = createObject(fieldType);
                    if (object != null)
                        method.invoke(emptyProduct, object);
                    else throw new IllegalArgumentException();
                }
            }
            tempClass = tempClass.getSuperclass();
        }
        return emptyProduct;
    }

    private Object createObject(Class<?> cls) {
        Object returnType = null;
        if (cls.isAssignableFrom(String.class))
            returnType = filler.fillString();
        if (cls.isAssignableFrom(int.class)) {
            returnType = filler.fillInt();
        } else if (cls.isAssignableFrom(Calendar.class)) {
            System.out.println(Localization.getResource("message.dateFormat") + ": " + ShopProperties.getProperty("date.format"));
            returnType = filler.fillDate();
        } else if (cls.isAssignableFrom(BigDecimal.class)) {
            returnType = filler.fillBigDecimal();
        }
        return returnType;
    }
}

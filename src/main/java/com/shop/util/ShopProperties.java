package com.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShopProperties {
    private static Properties properties;
    private static final Logger LOG = LogManager.getLogger(ShopProperties.class);
    private static final String pathToProperties = "properties.properties";

    public static boolean loadProperties() {
        properties = new Properties();
        try (InputStream input = ShopProperties.class.getClassLoader().getResourceAsStream(pathToProperties)) {
            properties.load(input);
            LOG.debug("Load properties");
            return true;
        } catch (IOException e) {
            LOG.debug("Cannot load properties");
            return false;
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String propName) {
        if (properties == null) loadProperties();
        return properties.getProperty(propName);
    }
}

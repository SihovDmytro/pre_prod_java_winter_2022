package com.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShopProperties {
    private static Properties properties;
    private static final Logger LOG = LogManager.getLogger(ShopProperties.class);
    public static final String pathToProperties = "properties.properties";

    public static boolean loadProperties() {
        properties = new Properties();
        try (InputStream input = ShopProperties.class.getClassLoader().getResourceAsStream(pathToProperties)) {
            properties.load(input);
            LOG.debug("Load properties");
            return true;
        } catch (IOException exception) {
            LOG.error("Cannot load properties",exception);
            return false;
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static String getProperty(String propName) {
        if(properties==null) loadProperties();
        return properties.getProperty(propName);
    }
}

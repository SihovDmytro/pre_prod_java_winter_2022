package com.webShop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationUtil {
    private static final Logger LOG = LogManager.getLogger(LocalizationUtil.class);
    private static final String BASENAME = "webShopLocalization";

    public static ResourceBundle getResourceBundle(Locale locale) {
        try {
            if (locale != null) {
                return ResourceBundle.getBundle(BASENAME, locale);
            }
        } catch (
                MissingResourceException exception) {
            LOG.error("Cannot find resource bundle", exception);
        }
        return null;
    }
}

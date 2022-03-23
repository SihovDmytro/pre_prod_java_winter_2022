package com.shop.util;


import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Localization {
    private static Properties properties;
    private static final String DEFAULT_LOCALE = ShopProperties.getProperty("localization.default");
    private static ResourceBundle bundle;

    public static void loadLocalization(Locale locale) {
        if (locale == null)
            bundle = ResourceBundle.getBundle("localization", new Locale(DEFAULT_LOCALE));
        else {
            bundle = ResourceBundle.getBundle("localization", locale);
        }
    }

    public static Locale getCurrentLocale() {
        return bundle.getLocale();
    }

    public static String getResource(String key) {
        return bundle.getString(key);
    }

}

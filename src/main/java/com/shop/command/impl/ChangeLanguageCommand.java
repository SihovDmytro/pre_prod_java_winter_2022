package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.util.Constants;
import com.shop.util.Localization;
import com.shop.util.MenuUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ChangeLanguageCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(ChangeLanguageCommand.class);
    private Scanner scanner;

    @Override
    public void execute() {
        LOG.trace("ChangeLanguageCommand start");
        MenuUtil.printLanguagesMenu();
        Map<String, Locale> locales = createLanguagesContainer();
        String option = scanner.nextLine();
        LOG.debug("option: " + option);
        Locale locale = locales.get(option);
        if (locale != null) {
            Localization.loadLocalization(locale);
            LOG.trace("locale changed to: " + Localization.getCurrentLocale());
        } else {
            System.out.println(Constants.UNKNOWN_LOCALE);
            LOG.info(Constants.UNKNOWN_LOCALE);
        }
        LOG.trace("ChangeLanguageCommand end");
    }

    private Map<String, Locale> createLanguagesContainer() {
        Map<String, Locale> map = new HashMap<>();
        map.put("1", new Locale("en_US"));
        map.put("2", new Locale("ru_UA"));
        return map;
    }
}

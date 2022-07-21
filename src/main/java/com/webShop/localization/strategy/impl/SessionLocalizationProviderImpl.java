package com.webShop.localization.strategy.impl;

import com.webShop.localization.strategy.LocalizationProvider;
import com.webShop.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public class SessionLocalizationProviderImpl implements LocalizationProvider {
    @Override
    public Optional<Locale> getLocale(HttpServletRequest request) {
        return Optional.ofNullable((Locale) request.getSession().getAttribute(Constants.CURRENT_LOCALE));
    }

    @Override
    public void setLocale(Locale locale, HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Constants.CURRENT_LOCALE, locale);
    }
}

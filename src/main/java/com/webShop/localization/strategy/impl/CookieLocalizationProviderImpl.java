package com.webShop.localization.strategy.impl;

import com.webShop.localization.strategy.LocalizationProvider;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public class CookieLocalizationProviderImpl implements LocalizationProvider {
    private static final Logger LOG = LogManager.getLogger(CookieLocalizationProviderImpl.class);

    @Override
    public Optional<Locale> getLocale(HttpServletRequest request) {
        return readCookie(request.getCookies()).map(Locale::new);
    }

    @Override
    public void setLocale(Locale locale, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(Constants.CURRENT_LOCALE, locale.getLanguage());
        try {
            int maxAge = Integer.parseInt(request.getServletContext().getInitParameter(Parameters.COOKIE_MAX_AGE));
            cookie.setMaxAge(maxAge);
        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse init parameter: " + Parameters.COOKIE_MAX_AGE);
        }
        response.addCookie(cookie);
    }

    private static Optional<String> readCookie(Cookie[] cookies) {
        for (Cookie tempCookie : cookies) {
            if (tempCookie.getName().equals(Constants.CURRENT_LOCALE)) {
                return Optional.ofNullable(tempCookie.getValue());
            }
        }
        return Optional.empty();
    }
}

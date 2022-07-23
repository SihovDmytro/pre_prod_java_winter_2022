package com.webShop.localization.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public interface LocalizationProvider {
    String SESSION = "session";
    String COOKIE = "cookie";

    Optional<Locale> getLocale(HttpServletRequest request);

    void setLocale(Locale locale, HttpServletRequest request, HttpServletResponse response);
}

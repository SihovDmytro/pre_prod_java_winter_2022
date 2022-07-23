package com.webShop.filter;

import com.webShop.entity.RequestWrapper;
import com.webShop.localization.strategy.LocalizationProvider;
import com.webShop.localization.strategy.impl.CookieLocalizationProviderImpl;
import com.webShop.localization.strategy.impl.SessionLocalizationProviderImpl;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LocalizationFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(LocalizationFilter.class);
    private List<Locale> supportedLocales;
    private Locale defaultLocale;
    private LocalizationProvider localizationProvider;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String supportedLocalesInitParam = filterConfig.getInitParameter(Parameters.SUPPORTED_LOCALES);
        String[] supportedLocales = supportedLocalesInitParam.split("\\|");
        String defaultLocale = filterConfig.getInitParameter(Parameters.DEFAULT_LOCALE);
        LOG.info("supportedLocales: " + Arrays.toString(supportedLocales));
        LOG.info("defaultLocale: " + defaultLocale);

        this.supportedLocales = new ArrayList<>();
        for (String language : supportedLocales) {
            this.supportedLocales.add(Locale.forLanguageTag(language));
        }
        this.defaultLocale = Locale.forLanguageTag(defaultLocale);
        validateLocales(this.supportedLocales, this.defaultLocale);
        filterConfig.getServletContext().setAttribute(Parameters.SUPPORTED_LOCALES, this.supportedLocales);

        String localizationProviderInitParam = filterConfig.getInitParameter(Parameters.LOCALIZATION_PROVIDER);
        LOG.trace("localizationProviderInitParam: " + localizationProviderInitParam);
        Map<String, LocalizationProvider> localizationProviderMap = getLocalizationProviderMap();
        localizationProvider = localizationProviderMap.getOrDefault(localizationProviderInitParam, new SessionLocalizationProviderImpl());
        LOG.info("localizationProvider: " + localizationProvider);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        LOG.trace("LocalizationFilter started: " + httpServletRequest.getRequestURL());

        Locale currLocale = null;
        String lang = request.getParameter(Parameters.LANG);
        LOG.info("lang: " + lang);
        if (lang != null && supportedLocales.contains(Locale.forLanguageTag(lang))) {
            currLocale = Locale.forLanguageTag(lang);
        } else {
            currLocale = localizationProvider.getLocale(httpServletRequest).map(locale -> {
                LOG.info("Locale found in storage");
                return locale;
            }).orElseGet(() -> {
                String header = httpServletRequest.getHeader(Constants.ACCEPT_LANGUAGE);
                LOG.trace("header: " + header);
                if (header == null) {
                    header = defaultLocale.toLanguageTag();
                }
                List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(header);
                Locale locale = Locale.lookup(languageRanges, supportedLocales);
                LOG.info("best match locale: " + locale);
                return locale;
            });
            if (currLocale == null) {
                LOG.info("set default locale");
                currLocale = defaultLocale;
            }
        }
        LOG.info("currLocale: " + currLocale);

        localizationProvider.setLocale(currLocale, httpServletRequest, httpServletResponse);
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest, currLocale);
        chain.doFilter(requestWrapper, httpServletResponse);
    }

    private void validateLocales(List<Locale> supportedLocales, Locale defaultLocale) throws ServletException {
        List<String> languages = Arrays.asList(Locale.getISOLanguages());

        if (supportedLocales.isEmpty()) {
            throw new ServletException("Cannot read supported locales");
        }
        for (Locale locale : supportedLocales) {
            if (!languages.contains(locale.getLanguage())) {
                throw new ServletException("Cannot parse supported locales. Non-existed language: " + locale.toLanguageTag());
            }
        }
        if (!languages.contains(defaultLocale.getLanguage()) || !supportedLocales.contains(defaultLocale)) {
            throw new ServletException("Cannot parse default locale: " + defaultLocale.toLanguageTag());
        }
    }

    private Map<String, LocalizationProvider> getLocalizationProviderMap() {
        Map<String, LocalizationProvider> map = new HashMap<>();
        map.put(LocalizationProvider.SESSION, new SessionLocalizationProviderImpl());
        map.put(LocalizationProvider.COOKIE, new CookieLocalizationProviderImpl());
        return map;
    }
}

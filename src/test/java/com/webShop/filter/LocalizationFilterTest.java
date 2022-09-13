package com.webShop.filter;

import com.webShop.localization.strategy.LocalizationProvider;
import com.webShop.util.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocalizationFilterTest {
    private static final String VALID_DEFAULT_LOCALE = "en-US";
    private static final String INVALID_SUPPORTED_LOCALES_PARAMETER = "abra|kadabra";
    private static final String VALID_SUPPORTED_LOCALES_PARAMETER = "ru|en-US|uk";
    private static final String INVALID_DEFAULT_LOCALE = "abrakadabra";
    private static final String LOCALE = "ru";
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(
            Locale.forLanguageTag(LOCALE),
            Locale.forLanguageTag("en-US"),
            Locale.forLanguageTag("uk"));
    private FilterConfig filterConfig;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private ServletContext context;
    private LocalizationProvider provider;
    private static final String LOCALIZATION_PROVIDER = "localizationProvider";

    @BeforeEach
    void setUp() {
        filterConfig = mock(FilterConfig.class);
        request = mock(HttpServletRequest.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        context = mock(ServletContext.class);
        provider = mock(LocalizationProvider.class);
    }

    @Test
    public void shouldThrowServletExceptionIfSupportedLocalesParameterIsInvalid() throws ServletException {
        when(filterConfig.getInitParameter(Parameters.SUPPORTED_LOCALES)).thenReturn(INVALID_SUPPORTED_LOCALES_PARAMETER);
        when(filterConfig.getInitParameter(Parameters.DEFAULT_LOCALE)).thenReturn(VALID_DEFAULT_LOCALE);
        assertThrows(ServletException.class, () -> new LocalizationFilter().init(filterConfig));
    }

    @Test
    public void shouldThrowServletExceptionIfDefaultLocaleParameterIsInvalid() throws ServletException {
        when(filterConfig.getInitParameter(Parameters.SUPPORTED_LOCALES)).thenReturn(VALID_SUPPORTED_LOCALES_PARAMETER);
        when(filterConfig.getInitParameter(Parameters.DEFAULT_LOCALE)).thenReturn(INVALID_DEFAULT_LOCALE);
        assertThrows(ServletException.class, () -> new LocalizationFilter().init(filterConfig));
    }

    @Test
    public void shouldSetUpDefaultLocaleAndSupportedLocales() throws ServletException {
        when(filterConfig.getInitParameter(Parameters.SUPPORTED_LOCALES)).thenReturn(VALID_SUPPORTED_LOCALES_PARAMETER);
        when(filterConfig.getInitParameter(Parameters.DEFAULT_LOCALE)).thenReturn(VALID_DEFAULT_LOCALE);
        when(filterConfig.getInitParameter(LOCALIZATION_PROVIDER)).thenReturn(LocalizationProvider.COOKIE);
        when(request.getLocale()).thenReturn(Locale.forLanguageTag(VALID_DEFAULT_LOCALE));
        when(filterConfig.getServletContext()).thenReturn(context);

        LocalizationFilter filter = new LocalizationFilter();
        filter.init(filterConfig);
        Locale defaultLocaleExpected = (Locale) Whitebox.getInternalState(filter, Parameters.DEFAULT_LOCALE);
        List<Locale> supportedLocalesExpected = (List<Locale>) Whitebox.getInternalState(filter, Parameters.SUPPORTED_LOCALES);

        boolean check = supportedLocalesExpected.equals(SUPPORTED_LOCALES) && defaultLocaleExpected.equals(Locale.forLanguageTag(VALID_DEFAULT_LOCALE));
        Assertions.assertTrue(check);
    }

    @Test
    public void shouldSetUpDefaultLocale() throws ServletException, IOException, NoSuchFieldException {
        when(provider.getLocale(any())).thenReturn(Optional.empty());

        Locale expected = new Locale(VALID_DEFAULT_LOCALE);

        LocalizationFilter filter = new LocalizationFilter();
        Whitebox.setInternalState(filter, Parameters.DEFAULT_LOCALE, expected);
        Whitebox.setInternalState(filter, Parameters.SUPPORTED_LOCALES, SUPPORTED_LOCALES);
        Whitebox.setInternalState(filter, Parameters.LOCALIZATION_PROVIDER, provider);
        filter.doFilter(request, response, chain);

        verify(provider, times(1)).setLocale(expected, request, response);
    }

    @Test
    public void shouldSetUpLocaleIfLangParamIsFound() throws ServletException, IOException {
        when(request.getParameter(Parameters.LANG)).thenReturn(LOCALE);

        LocalizationFilter filter = new LocalizationFilter();
        Whitebox.setInternalState(filter, Parameters.SUPPORTED_LOCALES, SUPPORTED_LOCALES);
        Whitebox.setInternalState(filter, Parameters.LOCALIZATION_PROVIDER, provider);
        filter.doFilter(request, response, chain);

        Locale expected = new Locale(LOCALE);
        verify(provider, times(1)).setLocale(expected, request, response);
    }

    @Test
    public void shouldSetUpLocaleIfItIsFoundInsideStorage() throws ServletException, IOException {
        Locale expected = new Locale(LOCALE);
        when(provider.getLocale(any())).thenReturn(Optional.of(expected));

        LocalizationFilter filter = new LocalizationFilter();
        Whitebox.setInternalState(filter, Parameters.SUPPORTED_LOCALES, SUPPORTED_LOCALES);
        Whitebox.setInternalState(filter, Parameters.LOCALIZATION_PROVIDER, provider);
        filter.doFilter(request, response, chain);

        verify(provider, times(1)).setLocale(expected, request, response);
    }

}
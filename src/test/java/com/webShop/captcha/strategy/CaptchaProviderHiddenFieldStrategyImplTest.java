package com.webShop.captcha.strategy;

import com.webShop.captcha.strategy.impl.CaptchaProviderHiddenFieldStrategyImpl;
import com.webShop.service.CaptchaService;
import com.webShop.service.impl.CaptchaServiceImpl;
import com.webShop.util.Attributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CaptchaProviderHiddenFieldStrategyImplTest {
    private CaptchaService captchaService = new CaptchaServiceImpl(new CaptchaProviderHiddenFieldStrategyImpl());
    private HttpServletRequest request;
    private HttpServletResponse response;
    private static ServletContext context;


    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
    }

    @Test
    public void shouldPutCaptchaIDInRequest() {
        when(request.getServletContext()).thenReturn(context);
        String captcha = "1234560";
        when(context.getAttribute(Attributes.CAPTCHA_MAP)).thenReturn(null);

        captchaService.addCaptcha(captcha, request, response);

        verify(request, times(1)).setAttribute(eq(Attributes.CAPTCHA_ID), anyString());
    }

    @Test
    public void shouldReturnCaptchaValue() {
        when(request.getServletContext()).thenReturn(context);
        String captchaValue = "1234560";
        Map<String, String> captchaMap = new HashMap<>();
        captchaMap.put("1231231", "222222");
        captchaMap.put("1", captchaValue);
        captchaMap.put("2", "654321");
        when(context.getAttribute(Attributes.CAPTCHA_MAP)).thenReturn(captchaMap);
        when(request.getParameter(Attributes.CAPTCHA_ID)).thenReturn("1");

        Assertions.assertEquals(captchaValue, captchaService.getCaptcha(request).get());
    }
}
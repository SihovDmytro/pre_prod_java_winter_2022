package com.webShop.captcha;

import com.webShop.util.Attributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CaptchaProviderCookieTest {
    private CaptchaProvider provider = new CaptchaProviderCookie();
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
    public void shouldPutCaptchaIDInCookie() {
        when(request.getServletContext()).thenReturn(context);
        String captcha = "1234560";
        when(context.getAttribute(Attributes.CAPTCHA_MAP)).thenReturn(null);

        provider.addCaptcha(captcha, request, response);

        verify(response, times(1)).addCookie(any());
    }

    @Test
    public void shouldReturnTrueIfCaptchaIsCorrect() {
        when(request.getServletContext()).thenReturn(context);
        String correctCaptcha = "1234560";
        Map<String, String> captchaMap = new HashMap<>();
        captchaMap.put("1231231", "222222");
        captchaMap.put("1", correctCaptcha);
        captchaMap.put("2", "654321");
        when(context.getAttribute(Attributes.CAPTCHA_MAP)).thenReturn(captchaMap);
        Cookie cookie = new Cookie(Attributes.CAPTCHA_ID, "1");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        Assertions.assertTrue(provider.checkCaptcha(correctCaptcha, request));
    }

    @Test
    public void shouldReturnFalseIfCaptchaIsNotCorrect() {
        when(request.getServletContext()).thenReturn(context);
        String notCorrectCaptcha = "1111111";
        String correctCaptcha = "1234560";
        Map<String, String> captchaMap = new HashMap<>();
        captchaMap.put("1231231", "222222");
        captchaMap.put("1", correctCaptcha);
        captchaMap.put("2", "654321");
        when(context.getAttribute(Attributes.CAPTCHA_MAP)).thenReturn(captchaMap);
        Cookie cookie = new Cookie(Attributes.CAPTCHA_ID, "1");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        Assertions.assertFalse(provider.checkCaptcha(notCorrectCaptcha, request));
    }
}
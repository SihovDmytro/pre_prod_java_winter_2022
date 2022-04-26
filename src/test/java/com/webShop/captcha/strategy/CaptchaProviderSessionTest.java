package com.webShop.captcha.strategy;

import com.webShop.util.Attributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CaptchaProviderSessionTest {
    private CaptchaProvider provider = new CaptchaProviderSession();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void shouldPutCaptchaInSession() {
        when(request.getSession()).thenReturn(session);
        String captcha = "1234560";

        provider.addCaptcha(captcha, request, response);

        verify(session, times(1)).setAttribute(eq(Attributes.CAPTCHA), anyString());
    }

    @Test
    public void shouldReturnCaptchaValue() {
        when(request.getSession()).thenReturn(session);
        String captchaValue = "1234560";
        when(session.getAttribute(Attributes.CAPTCHA)).thenReturn(captchaValue);

        Assertions.assertEquals(captchaValue, provider.getCaptcha(request));
    }
}
package com.webShop.servlet;

import com.webShop.entity.User;
import com.webShop.service.CaptchaService;
import com.webShop.service.UsersService;
import com.webShop.util.Attributes;
import com.webShop.util.AvatarConfig;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrationServletTest {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private ServletContext context;
    private RegistrationServlet servlet;
    private UsersService usersService;
    private CaptchaService captchaService;
    private HttpSession session;
    private Part part;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        context = mock(ServletContext.class);
        captchaService = mock(CaptchaService.class);
        servlet = mock(RegistrationServlet.class);
        usersService = mock(UsersService.class);
        session = mock(HttpSession.class);
        part = mock(Part.class);
    }

    @Test
    public void shouldCreateUserIfDataIsValidAndLoginIsUniqueAndCaptchaIsCorrect() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("validLogin");
        when(request.getParameter(Parameters.NAME)).thenReturn("validName");
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("validemail@mail.com");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("true");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("123456"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis());
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(0)).write(anyString());
        verify(usersService, times(1)).addUser(any());
    }

    @Test
    public void shouldNotCreateUserIfDataIsInvalid() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("12");
        when(request.getParameter(Parameters.NAME)).thenReturn(null);
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("invalidPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("123@312");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("12");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("123456"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis());
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(0)).write(anyString());
        verify(usersService, times(0)).addUser(any());
        verify(session, times(1)).setAttribute(eq(Attributes.ERRORS), anyMap());
        verify(response, times(1)).sendRedirect(Constants.REGISTRATION_SERVLET);
    }

    @Test
    public void shouldNotCreateUserIfLoginIsNotUnique() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("validLogin");
        when(request.getParameter(Parameters.NAME)).thenReturn("validName");
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("validemail@mail.com");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("true");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        User user = mock(User.class);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.of(user));
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("123456"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis());
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(0)).write(anyString());
        verify(usersService, times(0)).addUser(any());
        verify(session, times(1)).setAttribute(eq(Attributes.ERRORS), anyMap());
        verify(response, times(1)).sendRedirect(Constants.REGISTRATION_SERVLET);
    }

    @Test
    public void shouldNotCreateUserIfCaptchaIsNotCorrect() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("validLogin");
        when(request.getParameter(Parameters.NAME)).thenReturn("validName");
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("validemail@mail.com");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("true");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("not correct captcha"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis());
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(0)).write(anyString());
        verify(usersService, times(0)).addUser(any());
        verify(session, times(1)).setAttribute(eq(Attributes.ERRORS), anyMap());
        verify(response, times(1)).sendRedirect(Constants.REGISTRATION_SERVLET);
    }

    @Test
    public void shouldNotCreateUserIfPageHasExpired() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("validLogin");
        when(request.getParameter(Parameters.NAME)).thenReturn("validName");
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("validemail@mail.com");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("true");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getSubmittedFileName()).thenReturn("");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("123456"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1));
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(0)).write(anyString());
        verify(usersService, times(0)).addUser(any());
        verify(session, times(1)).setAttribute(eq(Attributes.ERRORS), anyMap());
        verify(response, times(1)).sendRedirect(Constants.REGISTRATION_SERVLET);
    }
    @Test
    public void shouldSaveAvatar() throws ServletException, IOException {
        when(request.getParameter(Parameters.LOGIN)).thenReturn("validLogin");
        when(request.getParameter(Parameters.NAME)).thenReturn("validName");
        when(request.getParameter(Parameters.SURNAME)).thenReturn("validSurname");
        when(request.getParameter(Parameters.PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.REPEAT_PASSWORD)).thenReturn("validPass");
        when(request.getParameter(Parameters.EMAIL)).thenReturn("validemail@mail.com");
        when(request.getParameter(Parameters.SEND_MAIL)).thenReturn("true");
        when(request.getParameter(Parameters.USER_CAPTCHA)).thenReturn("123456");
        when(request.getPart(Parameters.AVATAR)).thenReturn(part);
        when(part.getContentType()).thenReturn("image/png");
        when(part.getSize()).thenReturn(Long.valueOf(AvatarConfig.MAX_FILE_SIZE));
        when(part.getSubmittedFileName()).thenReturn("avatar.png");
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(usersService.getUserByLogin(any())).thenReturn(Optional.empty());
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.CAPTCHA_SERVICE)).thenReturn(captchaService);
        when(captchaService.getCaptcha(any())).thenReturn(Optional.of("123456"));
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Attributes.PAGE_GENERATION_TIME)).thenReturn(System.currentTimeMillis());
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(part, times(1)).write(anyString());
        verify(usersService, times(1)).addUser(any());
    }
}
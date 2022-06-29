package com.webShop.servlet;

import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.util.Attributes;
import com.webShop.util.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServletTest {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private ServletContext context;
    private LoginServlet servlet;
    private UsersService usersService;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        context = mock(ServletContext.class);
        servlet = mock(LoginServlet.class);
        usersService = mock(UsersService.class);
        session = mock(HttpSession.class);
    }

    @Test
    public void shouldPutUserInSessionIfCredentialsAreCorrect() throws ServletException, IOException {
        String login = "validLogin";
        String password = "validPass";
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(request.getParameter(Parameters.LOGIN)).thenReturn(login);
        when(request.getParameter(Parameters.PASSWORD)).thenReturn(password);
        when(usersService.login(login, password)).thenReturn(true);
        when(request.getSession()).thenReturn(session);
        User user = mock(User.class);
        when(usersService.getUserByLogin(login)).thenReturn(Optional.of(user));
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(session, times(1)).setAttribute(Attributes.CURRENT_USER, user);
    }

    @Test
    public void shouldPutUserInSessionIfCredentialsAreWrong() throws ServletException, IOException {
        String login = "validLogin";
        String password = "wrongPass";
        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute(Attributes.USERS_SERVICE)).thenReturn(usersService);
        when(request.getParameter(Parameters.LOGIN)).thenReturn(login);
        when(request.getParameter(Parameters.PASSWORD)).thenReturn(password);
        when(usersService.login(login, password)).thenReturn(false);
        when(request.getSession()).thenReturn(session);
        doCallRealMethod().when(servlet).init();
        doCallRealMethod().when(servlet).doPost(request, response);

        servlet.init();
        servlet.doPost(request, response);

        verify(session, times(0)).setAttribute(eq(Attributes.CURRENT_USER), any());
    }
}
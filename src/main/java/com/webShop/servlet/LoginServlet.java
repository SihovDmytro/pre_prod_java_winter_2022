package com.webShop.servlet;

import com.webShop.entity.LoginFormBean;
import com.webShop.entity.RegistrationFormBean;
import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/" + Constants.LOGIN_SERVLET)
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LoginServlet.class);
    private UsersService usersService;

    @Override
    public void init() throws ServletException {
        usersService = (UsersService) getServletContext().getAttribute(Attributes.USERS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doGet start");
        HttpSession session = req.getSession();
        Map<String, String> errors = (Map<String, String>) session.getAttribute(Attributes.ERRORS);
        req.setAttribute(Attributes.ERRORS, errors);
        session.removeAttribute(Attributes.ERRORS);
        req.getRequestDispatcher(Constants.LOGIN_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doPost start");
        LoginFormBean bean = readBean(req);
        LOG.debug("loginFormBean: " + bean);
        Map<String, String> errors = Validator.validateLogin(bean, usersService, req);
        LOG.debug("errors: " + errors);
        HttpSession session = req.getSession();
        if (errors.isEmpty()) {
            User user = usersService.getUserByLogin(bean.getLogin()).get();
            LOG.debug("user: " + user);
            session.setAttribute(Attributes.CURRENT_USER, user);
            resp.sendRedirect(Constants.LOGIN_PAGE_PATH);
        } else {
            session.setAttribute(Attributes.ERRORS, errors);
            resp.sendRedirect(Constants.LOGIN_SERVLET);
        }
    }

    private LoginFormBean readBean(HttpServletRequest request) {
        String login = request.getParameter(Parameters.LOGIN);
        String name = request.getParameter(Parameters.PASSWORD);
        return new LoginFormBean(login, name);
    }
}

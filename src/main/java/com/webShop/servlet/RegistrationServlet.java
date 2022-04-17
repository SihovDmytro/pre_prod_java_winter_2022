package com.webShop.servlet;

import com.shop.Runner;
import com.webShop.dao.impl.UsersDAOImpl;
import com.webShop.entity.RegistrationFormBean;
import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.service.impl.UsersServiceImpl;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(Constants.REGISTRATION_SERVLET)
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(RegistrationServlet.class);

    private UsersService usersService;

    @Override
    public void init() throws ServletException {
        LOG.trace("init start");
        List<User> users = new ArrayList<>();
        fillUsers(users);
        usersService = new UsersServiceImpl(new UsersDAOImpl(users));
        LOG.trace("init end");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doGet start");
        req.getRequestDispatcher(Constants.REGISTRATION_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doPost start");
        RegistrationFormBean bean = readBean(req);
        LOG.debug("bean: " + bean);
        Map<String, String> errors = Validator.validateRegistration(bean, usersService);
        LOG.debug("errors: " + errors.size());
        if (errors.isEmpty()) {
            User user = new User(bean);
            LOG.debug("user: "+user);
            usersService.addUser(user);
            resp.sendRedirect(Constants.LOGIN_PAGE_PATH);
        } else {
            req.setAttribute(Attributes.ERRORS, errors);
            doGet(req, resp);
        }
    }


    private RegistrationFormBean readBean(HttpServletRequest request) {
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String name = (String) request.getAttribute(Attributes.NAME);
        String surname = (String) request.getAttribute(Attributes.SURNAME);
        String password = (String) request.getAttribute(Attributes.PASSWORD);
        String passwordRepeat = (String) request.getAttribute(Attributes.REPEAT_PASSWORD);
        String email = (String) request.getAttribute(Attributes.EMAIL);
        boolean sendMail = (boolean) request.getAttribute(Attributes.SEND_MAIL);
        return new RegistrationFormBean(login, name, surname, password, passwordRepeat, email, sendMail);
    }

    private void fillUsers(List<User> users) {
        users.add(new User("dmytro", "Dmytro", "Sihov",
                "abrakadabra", "Dmytro_Sihov@epam.com", false));
        users.add(new User("vasya", "Vas", "Vass",
                "123456", "Vasya@epam.com", true));
        users.add(new User("petya", "Petya", "Pet",
                "qwerty", "Petya@epam.com", false));
        users.add(new User("dasdasasd", "adsasdasd", "asdasdasd",
                "asdasdasdas", "dasdasdda@epam.com", true));
    }
}

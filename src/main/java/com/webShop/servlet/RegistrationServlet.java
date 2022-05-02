package com.webShop.servlet;

import com.webShop.entity.RegistrationFormBean;
import com.webShop.entity.User;
import com.webShop.service.UsersService;
import com.webShop.util.Attributes;
import com.webShop.util.AvatarConfig;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import com.webShop.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@WebServlet("/" + Constants.REGISTRATION_SERVLET)
@MultipartConfig(fileSizeThreshold = AvatarConfig.FILE_SIZE_THRESHOLD,
        maxFileSize = AvatarConfig.MAX_FILE_SIZE,
        maxRequestSize = AvatarConfig.MAX_REQUEST_SIZE)
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(RegistrationServlet.class);

    private UsersService usersService;

    @Override
    public void init() throws ServletException {
        usersService = (UsersService) getServletContext().getAttribute(Attributes.USERS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("doGet start");
        HttpSession session = req.getSession();
        RegistrationFormBean bean = (RegistrationFormBean) session.getAttribute(Attributes.REGISTRATION_BEAN);
        req.setAttribute(Attributes.REGISTRATION_BEAN, bean);
        session.removeAttribute(Attributes.REGISTRATION_BEAN);
        Map<String, String> errors = (Map<String, String>) session.getAttribute(Attributes.ERRORS);
        req.setAttribute(Attributes.ERRORS, errors);
        session.removeAttribute(Attributes.ERRORS);
        req.getRequestDispatcher(Constants.REGISTRATION_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.trace("doPost start");
        RegistrationFormBean bean = readBean(req);
        LOG.debug("bean: " + bean);
        Map<String, String> errors = Validator.validateRegistration(bean, usersService, req);
        LOG.debug("errors: " + errors);
        if (errors.isEmpty()) {
            User user = new User(bean);
            LOG.debug("user: " + user);
            if (!Validator.isPartEmpty(bean.getAvatar())) {
                LOG.trace("Save user avatar");
                saveAvatar(bean, req);
            }
            usersService.addUser(user);
            resp.sendRedirect(Constants.LOGIN_PAGE_PATH);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute(Attributes.ERRORS, errors);
            session.setAttribute(Attributes.REGISTRATION_BEAN, bean);
            resp.sendRedirect(Constants.REGISTRATION_SERVLET);
        }
    }


    private RegistrationFormBean readBean(HttpServletRequest request) {
        String login = request.getParameter(Parameters.LOGIN);
        String name = request.getParameter(Parameters.NAME);
        String surname = request.getParameter(Parameters.SURNAME);
        String password = request.getParameter(Parameters.PASSWORD);
        String passwordRepeat = request.getParameter(Parameters.REPEAT_PASSWORD);
        String email = request.getParameter(Parameters.EMAIL);
        boolean sendMail = Boolean.parseBoolean(request.getParameter(Parameters.SEND_MAIL));
        String userCaptcha = request.getParameter(Parameters.USER_CAPTCHA);
        Part avatar = null;
        try {
            avatar = request.getPart(Parameters.AVATAR);
        } catch (IOException | ServletException exception) {
            LOG.error("Cannot get avatar", exception);
        }
        return new RegistrationFormBean(login, name, surname, password, passwordRepeat, email, sendMail, userCaptcha, avatar);
    }

    private void saveAvatar(RegistrationFormBean bean, HttpServletRequest request) {
        Part avatar = bean.getAvatar();
        String filename = request.getServletContext().getAttribute(Attributes.AVATARS_FOLDER) +
                File.separator + bean.getLogin() + AvatarConfig.AVATAR_EXTENSION;
        LOG.debug("filename: " + filename);
        try {
            avatar.write(filename);
        } catch (IOException exception) {
            LOG.error("Cannot save avatar", exception);
        }
    }
}

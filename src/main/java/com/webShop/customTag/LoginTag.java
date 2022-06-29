package com.webShop.customTag;

import com.webShop.entity.User;
import com.webShop.util.Attributes;
import com.webShop.util.AvatarConfig;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoginTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(LoginTag.class);
    private static final String LOGIN_FORM = "WEB-INF/jsp/loginForm.jsp";

    public static final String LEAVE_FORM = "WEB-INF/jsp/leaveForm.jsp";


    @Override
    public int doStartTag() {
        try {
            JspWriter writer = pageContext.getOut();
            HttpSession session = pageContext.getSession();
            User user = (User) session.getAttribute(Attributes.CURRENT_USER);
            if (user == null) {
                showLoginForm(writer);
            } else {
                showWelcomePage(writer, user);
            }
        } catch (IOException exception) {
            LOG.error("Cannot write content", exception);
        }
        return SKIP_BODY;
    }

    private void showLoginForm(JspWriter writer) throws IOException {
        Map<String, String> errors = (Map<String, String>) pageContext.getRequest().getAttribute(Attributes.ERRORS);
        String wrongCredentials = "";
        String invalidLogin = "";
        String invalidPassword = "";
        if (errors != null && !errors.isEmpty()) {
            wrongCredentials = Optional.ofNullable(errors.get(Attributes.CREDENTIALS)).orElse("");
            invalidLogin = Optional.ofNullable(errors.get(Parameters.LOGIN)).orElse("");
            invalidPassword = Optional.ofNullable(errors.get(Parameters.PASSWORD)).orElse("");
        }
        String loginForm = readFile(LOGIN_FORM);
        if (loginForm != null) {
            writer.println(String.format(loginForm,
                    Constants.LOGIN_SERVLET, wrongCredentials,
                    invalidLogin, invalidPassword,
                    Constants.REGISTRATION_PAGE_PATH));
        }
    }

    private void showWelcomePage(JspWriter writer, User user) throws IOException {
        String avatarFilename = AvatarConfig.AVATARS_FOLDER + File.separator + user.getLogin() + AvatarConfig.AVATAR_EXTENSION;
        if (!new File(pageContext.getServletContext().getRealPath(File.separator + avatarFilename)).exists()) {
            avatarFilename = AvatarConfig.AVATARS_FOLDER + File.separator + AvatarConfig.DEFAULT_AVATAR_FILE;
        }
        LOG.trace("pathToAvatar: " + pageContext.getServletContext().getRealPath(File.separator + avatarFilename));
        String leaveForm = readFile(LEAVE_FORM);
        if (leaveForm != null) {
            writer.write(String.format(leaveForm, Constants.LEAVE_SERVLET,
                    Optional.ofNullable(user.getName()).orElse(""),
                    avatarFilename));
        }
    }

    private String readFile(String path) {
        String text = null;
        path = pageContext.getServletContext().getRealPath(path);
        try {
            text = Files.lines(Paths.get(path), StandardCharsets.UTF_8).
                    collect(Collectors.joining("\n"));
        } catch (IOException exception) {
            LOG.error("Cannot read file", exception);
        }
        return text;
    }
}
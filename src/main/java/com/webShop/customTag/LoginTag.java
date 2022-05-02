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
import java.util.Map;
import java.util.Optional;

public class LoginTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(LoginTag.class);
    private static final String LOGIN_FORM =
            "            <form class=\"login-tag-form p-l-55 p-r-55 p-t-178\" action=\"%s\" method=\"post\">\n" +
                    "\t\t\t\t\t\t<span class=\"login100-form-title\">\n" +
                    "\t\t\t\t\t\t\t\tSign In\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\n" +
                    "                %s\n" +
                    "\n" +
                    "                <div class=\"wrap-input100 m-b-16\">\n" +
                    "                    <input class=\"input100\" type=\"text\" name=\"login\" id=\"login\" placeholder=\"Login\">\n" +
                    "                    <span id=\"loginError\">\n" +
                    "                        Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.\n" +
                    "                    </span>\n" +
                    "                    %s\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"wrap-input100\">\n" +
                    "                    <input class=\"input100\" type=\"password\" name=\"password\" id=\"password\" placeholder=\"Password\">\n" +
                    "                    <span id=\"passwordError\">\n" +
                    "                        Password must have at least 6 characters\n" +
                    "                    </span>\n" +
                    "                    %s\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"p-t-13 p-b-23\"></div>\n" +
                    "\n" +
                    "                <div class=\"container-login100-form-btn\">\n" +
                    "                    <button class=\"login100-form-btn\" id=\"submitLogin\">\n" +
                    "                        Login\n" +
                    "                    </button>\n" +
                    "                </div>\n" +
                    "\n" +
                    "                <div class=\"flex-col-c p-t-170 p-b-40\">\n" +
                    "\t\t\t\t\t\t<span class=\"txt1 p-b-9\">\n" +
                    "\t\t\t\t\t\t\tDon't have an account?\n" +
                    "\t\t\t\t\t\t</span>\n" +
                    "\n" +
                    "                    <a href=\"%s\" class=\"txt3\">\n" +
                    "                        Sign up now\n" +
                    "                    </a>\n" +
                    "                </div>\n" +
                    "            </form>\n";

    public static final String LEAVE_FORM =
            "<form class=\"login-tag-form p-l-55 p-r-55 p-t-178\" action=\"%s\" method=\"post\">\n" +
                    "\t\t\t\t\t<span class=\"login100-form-title\">\n" +
                    "\t\t\t\t\t\tWelcome, %s\n" +
                    "<img src=\"%s\" width=\"64\" height=\"64\">\n" +
                    "\t\t\t\t\t</span>\n" +
                    "\n" +
                    "                <div class=\"p-t-13 p-b-23\"></div>\n" +

                    "\n" +
                    "                <div class=\"container-login100-form-btn\">\n" +
                    "                    <button class=\"login100-form-btn\" id=\"leave\">\n" +
                    "                        Leave\n" +
                    "                    </button>\n" +
                    "                </div>\n" +
                    "            </form>";


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

        writer.println(String.format(LOGIN_FORM,
                Constants.LOGIN_SERVLET, wrongCredentials,
                invalidLogin, invalidPassword,
                Constants.REGISTRATION_PAGE_PATH));
    }

    private void showWelcomePage(JspWriter writer, User user) throws IOException {
        String avatarFilename = AvatarConfig.AVATARS_FOLDER + File.separator + user.getLogin() + AvatarConfig.AVATAR_EXTENSION;
        if (!new File(pageContext.getServletContext().getRealPath(File.separator + avatarFilename)).exists()) {
            avatarFilename = AvatarConfig.AVATARS_FOLDER + File.separator + AvatarConfig.DEFAULT_AVATAR_FILE;
        }
        LOG.trace("pathToAvatar: " + pageContext.getServletContext().getRealPath(File.separator + avatarFilename));
        writer.write(String.format(LEAVE_FORM, Constants.LEAVE_SERVLET,
                Optional.ofNullable(user.getName()).orElse(""),
                avatarFilename));
    }
}
package com.webShop.customTag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class LoginTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(LoginTag.class);
    private static final String LOGIN_FORM =
            "<form class=\"login100-form validate-form p-l-55 p-r-55 p-t-178\" action=\"${Constants.REGISTRATION_SERVLET}\" method=\"post\">\n" +
            "\t\t\t\t\t<span class=\"login100-form-title\">\n" +
            "\t\t\t\t\t\tSign Up\n" +
            "\t\t\t\t\t</span>\n" +
            "\n" +
            "                ${requestScope.errors.get(Attributes.PAGE_GENERATION_TIME)}\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-login m-b-16\">\n" +
            "                    <input class=\"input100\" type=\"text\" name=\"login\" id=\"login\" placeholder=\"Login\"\n" +
            "                           value=\"${requestScope.regBean.login}\">\n" +
            "                    <span id=\"loginError\">\n" +
            "                        Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.LOGIN)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-name m-b-16\">\n" +
            "                    <input class=\"input100\" type=\"text\" name=\"name\" id=\"name\" placeholder=\"Name\"\n" +
            "                           value=\"${requestScope.regBean.name}\">\n" +
            "                    <span id=\"nameError\">\n" +
            "                        Name must have at least 1 character\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.NAME)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-surname m-b-16\">\n" +
            "                    <input class=\"input100\" type=\"text\" name=\"surname\" id=\"surname\" placeholder=\"Surname\"\n" +
            "                           value=\"${requestScope.regBean.surname}\">\n" +
            "                    <span id=\"surnameError\">\n" +
            "                        Surname must have at least 1 character\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.SURNAME)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-password m-b-16\">\n" +
            "                    <input class=\"input100\" type=\"password\" name=\"password\" id=\"password\" placeholder=\"Password\">\n" +
            "                    <span id=\"passwordError\">\n" +
            "                        Password must have at least 6 characters\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.PASSWORD)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-password-repeat m-b-16\">\n" +
            "                    <input class=\"input100\" type=\"password\" name=\"repeat-password\" id=\"repeat-password\"\n" +
            "                           placeholder=\"Repeat password\">\n" +
            "                    <span id=\"passwordRepeatError\">\n" +
            "                        Passwords are different\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.REPEAT_PASSWORD)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100 validate-email\">\n" +
            "                    <input class=\"input100\" type=\"email\" name=\"email\" id=\"email\" placeholder=\"Email\"\n" +
            "                           value=\"${requestScope.regBean.email}\">\n" +
            "                    <span id=\"emailError\">\n" +
            "                        Please enter the email in the format: \"example@example.com\"\n" +
            "                    </span>\n" +
            "                    ${requestScope.errors.get(Parameters.EMAIL)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100\">\n" +
            "                    <input type=\"checkbox\" name=\"sendMail\" id=\"sendMail\" value=\"true\">\n" +
            "                    <label for=\"sendMail\">I want to receive the newsletter</label>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"wrap-input100\">\n" +
            "                    <cap:captchaTag/>\n" +
            "                    ${requestScope.errors.get(Parameters.USER_CAPTCHA)}\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"p-t-13 p-b-23\"></div>\n" +
            "\n" +
            "                <div class=\"container-login100-form-btn\">\n" +
            "                    <button class=\"login100-form-btn\" id=\"submitRegistration\">\n" +
            "                        Create\n" +
            "                    </button>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"flex-col-c p-t-170 p-b-40\">\n" +
            "                    <span class=\"txt1 p-b-9\">\n" +
            "                        Already registered?\n" +
            "                    </span>\n" +
            "                    <a href=\"${Constants.LOGIN_PAGE_PATH}\" class=\"txt3\">\n" +
            "                        Sign in\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "            </form>";

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.println(LOGIN_FORM);
        }catch (IOException exception){
            LOG.error("Cannot write content", exception);
        }
        return SKIP_BODY;
    }
}

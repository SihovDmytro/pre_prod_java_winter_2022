package com.webShop.customTag;


import com.webShop.servlet.RegistrationServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CaptchaTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(CaptchaTag.class);
    private static final String CAPTCHA_MAIN = "<img src=\"captcha-generator\" alt=\"captcha\">\n" +
            "<input class=\"input100\" type=\"text\" placeholder=\"Enter captcha\" name=\"userCaptcha\" id=\"userCaptcha\">";


    @Override
    public int doStartTag() {
        try {
            JspWriter writer = pageContext.getOut();
            writer.println(CAPTCHA_MAIN);
        } catch (IOException exception) {
            LOG.error("Cannot write a captcha", exception);
        }
        return SKIP_BODY;
    }
}

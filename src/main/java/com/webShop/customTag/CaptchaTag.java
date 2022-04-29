package com.webShop.customTag;


import com.webShop.captcha.strategy.CaptchaProviderStrategy;
import com.webShop.captcha.CaptchaSettings;
import com.webShop.service.CaptchaService;
import com.webShop.util.Attributes;
import com.webShop.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CaptchaTag extends TagSupport {
    private static final Logger LOG = LogManager.getLogger(CaptchaTag.class);
    private static final String CAPTCHA_MAIN = "<img src=\"captcha-generator?captcha=%s\" alt=\"captcha\">\n" +
            "<input class=\"input100\" type=\"text\" placeholder=\"Enter captcha\" name=\"userCaptcha\" id=\"userCaptcha\">";
    private static final String CAPTCHA_HIDDEN = "<input type=\"hidden\" id=\"captchaID\" name=\"captchaID\" value=\"%s\">";
    private static final String HIDDEN_FIELD = "hiddenField";

    @Override
    public int doStartTag() {
        try {
            String captcha = RandomUtil.generateRandomNumbers(CaptchaSettings.CAPTCHA_SIZE);
            LOG.debug("captcha: " + captcha);
            JspWriter writer = pageContext.getOut();
            CaptchaService captchaService = (CaptchaService) pageContext.getServletContext().getAttribute(Attributes.CAPTCHA_SERVICE);
            LOG.debug("captchaService: " + captchaService);
            captchaService.addCaptcha(captcha, (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
            writer.println(String.format(CAPTCHA_MAIN, captcha));
            if (pageContext.getServletContext().getInitParameter(Attributes.CAPTCHA_PROVIDER).equals(HIDDEN_FIELD)) {
                LOG.trace("write hidden field");
                String captchaID = (String) pageContext.getRequest().getAttribute(Attributes.CAPTCHA_ID);
                LOG.debug("captchaID: " + captchaID);
                writer.println(String.format(CAPTCHA_HIDDEN, captchaID));
            }
            pageContext.getSession().setAttribute(Attributes.PAGE_GENERATION_TIME, System.currentTimeMillis());
        } catch (IOException exception) {
            LOG.error("Cannot write a captcha", exception);
        }
        return SKIP_BODY;
    }
}

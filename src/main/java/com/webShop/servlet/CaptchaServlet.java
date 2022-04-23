package com.webShop.servlet;

import com.github.cage.GCage;
import com.oracle.webservices.internal.api.message.ContentType;
import com.webShop.captcha.CaptchaProvider;
import com.webShop.captcha.CaptchaProviderCookie;
import com.webShop.captcha.CaptchaProviderHiddenField;
import com.webShop.captcha.CaptchaProviderSession;
import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import com.webShop.util.RandomUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(Constants.CAPTCHA_SERVLET)
public class CaptchaServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(CaptchaServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GCage gCage = new GCage();
        resp.setContentType("image/" + gCage.getFormat());
        String captcha = RandomUtil.generateRandomNumbers(Constants.CAPTCHA_SIZE);
        LOG.debug("captcha: " + captcha);
        CaptchaProvider captchaProvider = (CaptchaProvider) getServletContext().getAttribute(Attributes.CAPTCHA_PROVIDER);
        LOG.debug("captchaProvider: " + captchaProvider);
        captchaProvider.addCaptcha(captcha, req, resp);
        gCage.draw(captcha, resp.getOutputStream());
    }
}

package com.webShop.servlet;

import com.github.cage.GCage;
import com.webShop.util.Constants;
import com.webShop.util.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/" + Constants.CAPTCHA_SERVLET)
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
        String captcha = req.getParameter(Parameters.CAPTCHA);
        LOG.debug("captcha: " + captcha);
        gCage.draw(captcha, resp.getOutputStream());
    }
}

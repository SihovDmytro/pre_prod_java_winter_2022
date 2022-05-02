package com.webShop.servlet;

import com.webShop.util.Attributes;
import com.webShop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/" + Constants.LEAVE_SERVLET)
public class LeaveServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LeaveServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.debug("doPost start");
        HttpSession session = req.getSession();
        if (session.getAttribute(Attributes.CURRENT_USER) != null) {
            session.removeAttribute(Attributes.CURRENT_USER);
        }
        resp.sendRedirect(Constants.LOGIN_PAGE_PATH);
    }
}

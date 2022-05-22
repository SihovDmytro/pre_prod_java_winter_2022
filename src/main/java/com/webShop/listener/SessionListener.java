package com.webShop.listener;

import com.webShop.dao.impl.CartDAOImpl;
import com.webShop.service.impl.CartServiceImpl;
import com.webShop.util.Attributes;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        sessionEvent.getSession().setAttribute(Attributes.CART, new CartServiceImpl(new CartDAOImpl()));
    }
}

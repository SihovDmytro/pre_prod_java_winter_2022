package com.webShop.servlet;

import com.webShop.entity.Product;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Operation {
    void execute(Product product) throws IOException, ServletException;
}

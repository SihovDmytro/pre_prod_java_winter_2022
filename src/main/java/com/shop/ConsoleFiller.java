package com.shop;

import com.shop.entity.Product;

import java.util.Scanner;

public class ConsoleFiller extends ProductFiller {
    private Scanner scanner;

    public ConsoleFiller(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Product fill(Product product) {
        return product.consoleInput(scanner);
    }
}

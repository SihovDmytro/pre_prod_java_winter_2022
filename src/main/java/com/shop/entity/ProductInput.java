package com.shop.entity;

import java.util.Scanner;

public interface ProductInput {
    Product consoleInput(Scanner scanner);

    Product randomInput();
}

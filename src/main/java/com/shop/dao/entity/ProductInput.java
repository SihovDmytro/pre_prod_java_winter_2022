package com.shop.dao.entity;

import java.util.Scanner;

public interface ProductInput {
    boolean consoleInput(Scanner scanner);
    boolean randomInput(int min, int max);
}

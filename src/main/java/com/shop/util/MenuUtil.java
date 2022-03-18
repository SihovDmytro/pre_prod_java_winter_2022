package com.shop.util;

public class MenuUtil {

    public static final String MAIN_MENU =
            "=====================================================\n" +
                    "0 - show all products\n" +
                    "1 - add to cart\n" +
                    "2 - show cart content\n" +
                    "3 - make order\n" +
                    "4 - show last 5 items in the cart\n" +
                    "5 - show all orders for period\n" +
                    "6 - show order by date\n" +
                    "-1 - leave\n" +
                    "=====================================================\n";

    public static void printMenu() {
        System.out.println(MAIN_MENU);
    }
}

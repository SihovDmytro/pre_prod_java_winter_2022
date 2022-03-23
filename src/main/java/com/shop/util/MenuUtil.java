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
                    "7 - add a new product to assortment\n" +
                    "8 - add a new product to assortment using reflection\n" +
                    "9 - change language\n" +
                    "-1 - leave\n" +
                    "=====================================================\n";
    public static final String PRODUCT_INPUT_MENU =
            "=====================================================\n" +
                    "1 - input from console\n" +
                    "2 - random input\n" +
                    "=====================================================\n";

    public static final String PRODUCT_LIST_MENU =
            "=====================================================\n" +
                    "1 - furniture\n" +
                    "2 - food\n" +
                    "3 - canned food\n" +
                    "=====================================================\n";

    public static final String LANGUAGES_MENU =
            "=====================================================\n" +
                    "1 - English\n" +
                    "2 - Русский\n" +
                    "=====================================================\n";

    public static void printMainMenu() {
        System.out.println(MAIN_MENU);
    }

    public static void printProductInputMenu() {
        System.out.println(PRODUCT_INPUT_MENU);
    }

    public static void printProductListMenu() {
        System.out.println(PRODUCT_LIST_MENU);
    }

    public static void printLanguagesMenu() {
        System.out.println(LANGUAGES_MENU);
    }
}

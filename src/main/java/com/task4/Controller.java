package com.task4;

import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            printMenu();
            switch (scanner.next().trim())
            {
                case "1":{
                    System.out.println("select1");
                    break;
                }
                default:{
                    System.out.println("Unknown operation.");
                }
            }
        }
    }

    private static void printMenu()
    {
        System.out.println("1 - select all products\n" +
                "2 - add product to cart\n" +
                "3 - show cart conten\n" +
                "4 - make order\n" +
                "5 - show last 5 products in the cart\n" +
                "6 - leave\n" +
                "Select option: ");
    }
}

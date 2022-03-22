package com.shop.strategy;

import com.shop.util.DateUtil;
import com.shop.util.ShopProperties;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class ConsoleFiller extends Filler {
    private Scanner scanner;

    public ConsoleFiller(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public String fillString() {
        return scanner.nextLine();
    }

    @Override
    public int fillInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public BigDecimal fillBigDecimal() {
        return new BigDecimal(scanner.nextLine());
    }

    @Override
    public Calendar fillDate() {
        SimpleDateFormat format = new SimpleDateFormat(ShopProperties.getProperty("date.format"));
        Calendar calendar = DateUtil.readDateFromConsole(scanner, format);
        if (calendar == null) throw new IllegalArgumentException();
        return calendar;
    }
}

package com.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class Util {
    private static final Logger LOG = LogManager.getLogger(Util.class);

    public static String calendarToStringDate(Calendar calendar) {
        return new SimpleDateFormat(ShopProperties.getProperty("date.format")).format(calendar.getTime());
    }

    public static String calendarToStringDatetime(Calendar calendar) {
        return new SimpleDateFormat(ShopProperties.getProperty("datetime.format")).format(calendar.getTime());
    }

    public static Calendar stringToCalendar(String stringDate, SimpleDateFormat format) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(stringDate));
            return calendar;
        } catch (ParseException exception) {
            LOG.trace("Cannot parse order date");
            return null;
        }
    }


    public static String randomString(String base, int min, int max) {
        return base + " " + (new Random().nextInt(max - min) + min);
    }

    public static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static Calendar randomCalendar() {
        Calendar randCalendar = Calendar.getInstance();
        randCalendar.setTimeInMillis(new Random().nextLong());
        return randCalendar;
    }

    public static String getSetterName(String field) {

        return "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}

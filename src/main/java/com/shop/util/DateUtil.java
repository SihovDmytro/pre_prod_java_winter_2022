package com.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    private static final Logger LOG = LogManager.getLogger(DateUtil.class);

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
}

package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateSupport {
    private static final Logger logger = LoggerFactory.getLogger("logger");

    public DateSupport() {
    }

    public static Date strForDate(String date, String format) {
        Date dateTime = null;
        if (date != null && !"".equals(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);

            try {
                dateTime = formatter.parse(date);
            } catch (ParseException var5) {
                logger.error(var5.getMessage(), var5);
            }

            return dateTime;
        } else {
            return dateTime;
        }
    }

    public static String dateForString(Date date, String format) {
        String dateTime = "";
        if (date == null) {
            return dateTime;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateTime = formatter.format(date);
            return dateTime;
        }
    }

    public static String datePlusDay(String strDate, int plus) {
        Date date = strForDate(strDate, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int D = calendar.get(5);
        date.setDate(D + plus);
        return dateForString(date, "yyyy-MM-dd");
    }

    public static Calendar getDateCalendar(String strDate) {
        Date date = strForDate(strDate, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int getDateMonthLastDay(String strDate) {
        Calendar calendar = getDateCalendar(strDate);
        return calendar.getActualMaximum(2);
    }
}


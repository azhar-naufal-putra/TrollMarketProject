package com.TrollMarket.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {
    public static String dateParse(LocalDate date, String pattern, Locale locale){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        String dateFormatted = date.format(formatter);
        return dateFormatted;
    }
}

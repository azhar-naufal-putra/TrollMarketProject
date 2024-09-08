package com.TrollMarket.helpers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceHelper {
    public static String parsePrice(BigDecimal price, Locale format){
        return NumberFormat.getCurrencyInstance(format).format(price);
    }
}

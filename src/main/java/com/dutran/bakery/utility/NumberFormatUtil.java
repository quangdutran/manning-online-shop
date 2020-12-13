package com.dutran.bakery.utility;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatUtil {
    @Value("${application.language}")
    public static String language;

    @Value("${application.country}")
    public static String country;


    public static Locale getLocale() {
        return new Locale(language, country);
    }

    public static NumberFormat getCurrencyFormat() {
        return NumberFormat.getCurrencyInstance(getLocale());
    }

    public static String formatPrice(BigDecimal price) {
        return getCurrencyFormat().format(price);
    }

    public static String formatPrice(double price) {
        return getCurrencyFormat().format(price);
    }

}

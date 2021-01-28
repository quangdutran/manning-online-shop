package com.dutran.outlet.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Component
@Getter
@Setter
public class AppConfig {
    @Value("${application.language}")
    public String language;

    @Value("${application.country}")
    public String country;

    public Locale getLocale() {
        return new Locale(language, country);
    }

    public NumberFormat getCurrencyFormat() {
        return NumberFormat.getCurrencyInstance(getLocale());
    }

    public String formatPrice(BigDecimal price) {
        return getCurrencyFormat().format(price);
    }
}

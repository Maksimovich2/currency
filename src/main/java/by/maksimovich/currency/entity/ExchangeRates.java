package by.maksimovich.currency.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Maksim Maksimovich
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRates {
    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, BigDecimal> rates;
}

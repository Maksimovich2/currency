package by.maksimovich.currency.service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Maksim Maksimovich
 */
public interface ExchangeRatesService {
    List<String> findAllCodes();

    BigDecimal findDifferenceStatus(String codeName);

    void refreshExchangeRates();
}

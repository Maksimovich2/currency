package by.maksimovich.currency;

import by.maksimovich.currency.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Maksim Maksimovich
 */
@Component
public class DataInit {

    private final ExchangeRatesService exchangeRatesService;

    @Autowired
    public DataInit(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @PostConstruct
    public void firstDataInit() {
        exchangeRatesService.refreshExchangeRates();
    }
}

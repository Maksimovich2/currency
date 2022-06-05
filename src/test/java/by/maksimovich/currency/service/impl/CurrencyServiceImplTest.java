package by.maksimovich.currency.service.impl;

import by.maksimovich.currency.service.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maksim Maksimovich
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CurrencyServiceImplTest {

    @Autowired
    private CurrencyService currencyService;

    @Test
    void showGifByCurrencyDifference() {
        String gifLink = currencyService.showGifByCurrencyDifference("RUB");
        Assertions.assertNotNull(gifLink);
    }
}
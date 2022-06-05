package by.maksimovich.currency.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Maksim Maksimovich
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("by.maksimovich.currency")
@AutoConfigureMockMvc
class ExchangeRatesServiceImplTest {

    @Autowired
    private ExchangeRatesServiceImpl exchangeRatesService;

    @Test
    void findAllCodes() {
        List<String> actualCodes = exchangeRatesService.findAllCodes();
        Assertions.assertNotNull(actualCodes);
        Assertions.assertEquals(169, actualCodes.size());
    }

    @Test
    void findDifferenceStatus() {
        Assertions.assertNotNull(exchangeRatesService.findDifferenceStatus("RUB"));
    }
}
package by.maksimovich.currency.controller;

import by.maksimovich.currency.service.CurrencyService;
import by.maksimovich.currency.service.ExchangeRatesService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Maksim Maksimovich
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final ExchangeRatesService exchangeRatesService;
    private final CurrencyService currencyService;

    @GetMapping("/find-all-currencies-codes")
    public List<String> findAllCurrenciesCodes() {
        return exchangeRatesService.findAllCodes();
    }

    @SneakyThrows
    @GetMapping("/show-gif-by-currency-difference/{currencyCodeName}")
    public void showGifByCurrencyDifference(@PathVariable String currencyCodeName, HttpServletResponse response) {
        response.sendRedirect(currencyService.showGifByCurrencyDifference(currencyCodeName));
    }
}

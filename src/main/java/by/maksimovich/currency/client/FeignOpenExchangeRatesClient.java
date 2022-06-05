package by.maksimovich.currency.client;

import by.maksimovich.currency.entity.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Maksim Maksimovich
 */
@FeignClient(name = "OERClient", url = "${openexchangerates.url.general}")
public interface FeignOpenExchangeRatesClient {

    @GetMapping("/latest.json")
    ExchangeRates showLatestRates(
            @RequestParam("app_id") String appId
    );

    @GetMapping("/historical/{date}.json")
    ExchangeRates showHistoricalRate(
            @PathVariable String date,
            @RequestParam("app_id") String appId
    );
}

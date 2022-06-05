package by.maksimovich.currency.service.impl;

import by.maksimovich.currency.dto.ResponseDto;
import by.maksimovich.currency.service.CurrencyService;
import by.maksimovich.currency.service.ExchangeRatesService;
import by.maksimovich.currency.service.GifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Maksim Maksimovich
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final ExchangeRatesService exchangeRatesService;
    private final GifService gifService;

    @Value("${giphy.increased}")
    private String increasedTag;
    @Value("${giphy.fallen}")
    private String fallenTag;
    @Value("${giphy.same}")
    private String sameTag;

    @Override
    public String showGifByCurrencyDifference(String currencyCodeName) {
        BigDecimal differenceBetweenRates = exchangeRatesService.findDifferenceStatus(currencyCodeName);
        String gifTag;
        if (differenceBetweenRates.compareTo(new BigDecimal(0)) > 0) {
            gifTag = increasedTag;
            log.info("the rate has increased");
        } else if (differenceBetweenRates.compareTo(new BigDecimal(0)) < 0){
            gifTag = fallenTag;
            log.info("the rate has fallen");
        } else {
            gifTag = sameTag;
            log.info("the course remained the same");
        }
        ResponseDto result = gifService.showGif(gifTag);
        return result.getData().getImages().getFixed_height_downsampled().getUrl();
    }
}

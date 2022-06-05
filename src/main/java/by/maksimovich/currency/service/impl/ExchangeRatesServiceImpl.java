package by.maksimovich.currency.service.impl;

import by.maksimovich.currency.client.FeignOpenExchangeRatesClient;
import by.maksimovich.currency.entity.ExchangeRates;
import by.maksimovich.currency.service.ExchangeRatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author Maksim Maksimovich
 */
@Service
@Slf4j
public class ExchangeRatesServiceImpl implements ExchangeRatesService {
    private ExchangeRates prevRates;
    private ExchangeRates currentRates;

    private final FeignOpenExchangeRatesClient feignOpenExchangeRatesClient;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat timeFormat;

    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    public ExchangeRatesServiceImpl(
            FeignOpenExchangeRatesClient feignOpenExchangeRatesClient,
            @Qualifier("date_bean") SimpleDateFormat dateFormat,
            @Qualifier("time_bean") SimpleDateFormat timeFormat
    ) {
        this.feignOpenExchangeRatesClient = feignOpenExchangeRatesClient;
        this.dateFormat = dateFormat;
        this.timeFormat = timeFormat;
    }

    /**
     * Возвращает список доступных для проверки валют.
     *
     * @return список доступных валют
     */
    @Override
    public List<String> findAllCodes() {
        if (currentRates.getRates() != null) {
            return new ArrayList<>(currentRates.getRates().keySet());
        } else {
            log.info("something went wrong in ExchangeRatesService class and findAllCodes method");
            throw new RuntimeException("incorrect data");
        }
    }

    /**
     * Проверяет\обновляет курсы,
     *
     * @param currencyCodeName код валюты для сравнения
     * @return Возвращает разность сравнения коэфициентов.
     */
    @Override
    public BigDecimal findDifferenceStatus(String currencyCodeName) {
        refreshExchangeRates();
        BigDecimal prevCoefficient = getCoefficient(prevRates, currencyCodeName.toUpperCase());
        BigDecimal currentCoefficient = getCoefficient(currentRates, currencyCodeName.toUpperCase());
        return currentCoefficient.subtract(prevCoefficient);
    }

    /**
     * Проверка\обновление курсов.
     */
    @Override
    public void refreshExchangeRates() {
        long currentTime = System.currentTimeMillis();
        refreshCurrentRates(currentTime);
        refreshPrevRates(currentTime);
    }

    /**
     * Обновление текущих курсов.
     * Проверяется время с точностью до часа, т.к.
     * Обновление на openexchangerates.org происходит каждый час.
     *
     * @param time время с точность до часа
     */
    private void refreshCurrentRates(long time) {
        if (
                currentRates == null ||
                        !timeFormat.format(Long.valueOf(currentRates.getTimestamp()) * 1000)
                                .equals(timeFormat.format(time))
        ) {
            currentRates = feignOpenExchangeRatesClient.showLatestRates(appId);
        }
    }

    /**
     * Обновление вчерашних курсов.
     * Проверяется время с точностью до дня.
     * Так же, что бы при каждом запросе не приходилось обращаться к внешнему сервису
     * для обновления курсов- происходит проверка даты текущего prevRates
     * с приведённым к строковому виду YYYY-MM-DD текущей и меньшей на день от текущей дат,
     * т.к. при запросе к к openexchangerates.org//historical/* с указанием
     * вчерашней даты могут вернуться
     * курсы с датой, равной текущей.
     *
     * @param time время проверки курсов
     */
    private void refreshPrevRates(long time) {
        Calendar prevCalendar = Calendar.getInstance();
        prevCalendar.setTimeInMillis(time);
        String currentDate = dateFormat.format(prevCalendar.getTime());
        prevCalendar.add(Calendar.DAY_OF_YEAR, -1);
        String newPrevDate = dateFormat.format(prevCalendar.getTime());
        if (
                prevRates == null
                        || (
                        !dateFormat.format(Long.valueOf(prevRates.getTimestamp()) * 1000)
                                .equals(newPrevDate)
                                && !dateFormat.format(Long.valueOf(prevRates.getTimestamp()) * 1000)
                                .equals(currentDate)
                )
        ) {
            prevRates = feignOpenExchangeRatesClient.showHistoricalRate(newPrevDate, appId);
        }
    }

    /**
     * Формула для подсчётка коэфициента по отношению к установленной в этом приложении валютной базе.
     * (Default_Base / Our_Base) * Target_Currency
     * Если на входе оказался несуществующий currencyCodeName- то вернёт null
     *
     * @param rates курсы валют
     * @param currencyCodeName код проверяемой валюты
     */
    private BigDecimal getCoefficient(ExchangeRates rates, String currencyCodeName) {
        BigDecimal result = null;
        BigDecimal targetRate = null;
        BigDecimal appBaseRate = null;
        BigDecimal defaultBaseRate = null;
        Map<String, BigDecimal> map;
        if (rates != null && rates.getRates() != null) {
            map = rates.getRates();
            targetRate = map.get(currencyCodeName);
            appBaseRate = map.get(base);
            defaultBaseRate = map.get(rates.getBase());
        }
        if (targetRate != null && appBaseRate != null && defaultBaseRate != null) {
            result = (defaultBaseRate.divide(appBaseRate, RoundingMode.CEILING))
                            .multiply(targetRate);
        }
        return result;
    }
}

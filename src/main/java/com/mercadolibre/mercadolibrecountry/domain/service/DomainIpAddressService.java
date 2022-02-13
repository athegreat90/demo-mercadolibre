package com.mercadolibre.mercadolibrecountry.domain.service;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.domain.service.util.IpValidation;
import com.mercadolibre.mercadolibrecountry.sources.redis.RedisClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.CountryInfoRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.FixerRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.IpApiRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class DomainIpAddressService implements IpAddressService {

    private final IpApiRestClient ipApiRestClient;
    private final CountryInfoRestClient countryInfoRestClient;
    private final FixerRestClient fixerRestClient;
    private final RedisClient redisClient;


    @Override
    public IpAddress findIpAddress(String ipAddress) {
        IpAddress response = IpAddress.builder().address(ipAddress).build();

        Country countryByIp = findCountryByIp(ipAddress);


        if (countryByIp != null && countryByIp.getIsoCode() != null) {
            Country countryByCode = findCountryDetail(countryByIp.getIsoCode());
            response.setCountry(countryByCode);
        }

        List<Currency> currenciesResponse = new ArrayList<>();
        if (response.getCountry() != null && response.getCountry().getCurrencies() != null && !response.getCountry().getCurrencies().isEmpty()) {
            for (Currency currency : response.getCountry().getCurrencies()) {
                Currency currencyResponse = Currency.builder()
                        .symbol(currency.getSymbol())
                        .code(currency.getCode())
                        .name(currency.getName())
                        .build();

                Double currencyRateUSD = findCurrencyValue(currency.getCode(), "USD");
                Double currencyRateEuro = findCurrencyValue(currency.getCode(), "EUR");

                currencyResponse.setUsdRate(currencyRateUSD);
                currencyResponse.setEurosRate(currencyRateEuro);

                currenciesResponse.add(currencyResponse);
            }

            response.getCountry().setCurrencies(currenciesResponse);
        }

        return response;
    }

    public Country findCountryByIp(String ipAddress)
    {
        log.info("IP: {}", ipAddress);
        boolean isPublicIp = IpValidation.isPublicIp(ipAddress);

        Country countryByIp = null;


        if (isPublicIp) {
            countryByIp = redisClient.findIpLocation(ipAddress);
            if (countryByIp == null) {
                countryByIp = ipApiRestClient.findCountryByIp(ipAddress);

                redisClient.saveIpLocation(
                        IpAddress.builder()
                                .address(ipAddress)
                                .country(countryByIp)
                                .build()
                );
            }

        }
        return countryByIp;
    }

    public Country findCountryDetail(String countryIsoCode) {
        Country countryByCode = null;
        countryByCode = redisClient.findCountryDetailByCode(countryIsoCode);

        if (countryByCode == null) {
            countryByCode = countryInfoRestClient.findCountryByIsoCode(countryIsoCode);

            redisClient.saveCountryDetail(countryByCode);
        }
        return countryByCode;
    }

    public Double findCurrencyValue(String from, String to) {

        Double rate = null;
        rate = redisClient.findCurrencyRate(from, to);

        if (rate == null || rate == 0.0) {
            rate = fixerRestClient.findRate(from, to);

            if (rate > 0.0) {
                redisClient.saveCurrencyRate(from, to, rate);
            }
        }

        return rate;
    }
}

package com.mercadolibre.mercadolibrecountry.domain.service;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.sources.redis.RedisClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.CountryInfoRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.FixerRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.IpApiRestClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class DomainIpAddressServiceTest {

    @MockBean
    private IpApiRestClient ipApiRestClient;

    @MockBean
    private CountryInfoRestClient countryInfoRestClient;

    @MockBean
    private FixerRestClient fixerRestClient;

    @MockBean
    private RedisClient redisClient;

    @Autowired
    private DomainIpAddressService domainIpAddressService;

    @BeforeEach
    void setUp_rest_us_then_redis()
    {
        // Country by Ip - US
        Country mockedCountryByIp = Country.builder().name("United States").isoCode("US").build();
        when(ipApiRestClient.findCountryByIp("8.8.8.8")).thenReturn(mockedCountryByIp);

        // Country by code
        Currency mockedCurrency = Currency.builder().name("United States Dollar").code("USD").symbol("$").build();
        List<Currency> mockedCurrencies = new ArrayList<>();
        mockedCurrencies.add(mockedCurrency);

        Country mockedCountryByCode = Country.builder().name("United States").isoCode("US").currencies(mockedCurrencies).build();

        when(countryInfoRestClient.findCountryByIsoCode("US")).thenReturn(mockedCountryByCode);

        //Currency Rates for usd
        when(fixerRestClient.findRate("USD", "USD")).thenReturn(1.0);
        when(fixerRestClient.findRate("USD", "EUR")).thenReturn(0.8);

        //save data in redis
        //nothing

    }

    // REST CLIENTS
    @Test
    void when_findCountryByIp_then_success() {
        Country country = domainIpAddressService.findCountryByIp("8.8.8.8");
        assertEquals("US", country.getIsoCode());
    }


    @Test
    void when_findCountryDetails_US_then_REST_success() {
        String isoCode = "US";
        Country countryDetail = domainIpAddressService.findCountryDetail(isoCode);
        assertEquals("United States", countryDetail.getName());
        assertEquals("US", countryDetail.getIsoCode());
    }

    @Test
    void when_findCurrenciesValues_US_then_REST_success() {
        List<Currency> currencies = new ArrayList<>();
        currencies.add(
                Currency.builder().code("USD").build()
        );

        for (Currency currency: currencies) {
            Double currencyUsdValue= domainIpAddressService.findCurrencyValue(currency.getCode(), "USD");
            Double currencyEuroValue= domainIpAddressService.findCurrencyValue(currency.getCode(), "EUR");
            assertEquals(0.8,currencyEuroValue);
            assertEquals(1, currencyUsdValue);
        }
    }

    @Test
    void when_ipAddress_isPublic_US_then_REST_success() {
        IpAddress response = domainIpAddressService.findIpAddress("8.8.8.8");
        assertEquals("United States", response.getCountry().getName());
        assertEquals("US", response.getCountry().getIsoCode());
        assertEquals("USD", response.getCountry().getCurrencies().get(0).getCode());
        assertEquals(0.8, response.getCountry().getCurrencies().get(0).getEurosRate());
    }


    @Test
    void when_ipAddress_isPrivate_US_then_REST_failure() {
        IpAddress response = domainIpAddressService.findIpAddress("192.168.0.100");
        assertNull(response.getCountry());

        response = domainIpAddressService.findIpAddress("172.23.56.89");
        assertNull(response.getCountry());
    }




    // REDIS CLIENTS
    @Test
    void when_findCountryByIp_CO_then_REST_success() {
        Country mockedCountryByIp = Country.builder().name("Colombia").isoCode("CO").build();
        when(redisClient.findIpLocation("181.237.144.170")).thenReturn(mockedCountryByIp);
        Country country = domainIpAddressService.findCountryByIp("181.237.144.170");
        assertEquals("CO", country.getIsoCode());
    }


    @Test
    void when_findCountryDetails_CO_then_REST_success() {
        String isoCode = "CO";
        Currency mockedCurrency = Currency.builder().name("Colombian Peso").code("COP").symbol("$").build();
        List<Currency> mockedCurrencies = new ArrayList<>();
        mockedCurrencies.add(mockedCurrency);
        Country mockedCountryByCode = Country.builder().name("Colombia").isoCode("CO").currencies(mockedCurrencies).build();

        when(redisClient.findCountryDetailByCode("CO")).thenReturn(mockedCountryByCode);
        Country countryDetail = domainIpAddressService.findCountryDetail(isoCode);
        assertEquals("Colombia", countryDetail.getName());
        assertEquals(isoCode, countryDetail.getIsoCode());
    }

    @Test
    void when_findCurrenciesValues_CO_then_REST_success()
    {
        Currency mockedCurrency = Currency.builder().name("Colombian Peso").code("COP").symbol("$").build();
        List<Currency> mockedCurrencies = new ArrayList<>();
        mockedCurrencies.add(mockedCurrency);
        Country mockedCountryByCode = Country.builder().name("Colombia").isoCode("CO").currencies(mockedCurrencies).build();

        when(redisClient.findCountryDetailByCode("COP")).thenReturn(mockedCountryByCode);
        when(redisClient.findCurrencyRate("COP", "USD")).thenReturn(0.005);
        when(redisClient.findCurrencyRate("COP", "EUR")).thenReturn(0.004);
        Double currencyUsdValue= domainIpAddressService.findCurrencyValue("COP", "USD");
        Double currencyEuroValue= domainIpAddressService.findCurrencyValue("COP", "EUR");
        assertEquals(0.005, currencyUsdValue);
        assertEquals(0.004, currencyEuroValue);
    }


}
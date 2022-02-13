package com.mercadolibre.mercadolibrecountry.sources.rest.countryinfo;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.CountryInfoRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response.CurrencyResponseDto;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response.ResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CountryInfoRestClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CountryInfoRestClient countryInfoRestClient;

    @Test
    void when_code_is_co_then_success() {
        List<CurrencyResponseDto> currencies = new ArrayList<>();
        currencies.add(new CurrencyResponseDto("COP", "Colombian Peso", "$"));
        ResponseDto responseDto = ResponseDto.builder()
                .name("Colo0mbia")
                .alpha2Code("CO")
                .currencies(currencies)
                .build();

        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(
                responseDto, HttpStatus.OK
        );

        Mockito.doReturn(responseEntity).when(restTemplate)
                .getForEntity(
                        anyString(), any()
                );

        Country clientResponse = countryInfoRestClient.findCountryByIsoCode("CO");

        assertNotNull(clientResponse.getIsoCode(), "country code must have a value");
        assertNotNull(clientResponse.getName(), "country name must have a value");
        assertFalse(clientResponse.getCurrencies().isEmpty(), "country must have at lkeast one currency");

    }

    @Test
    void when_code_is_xx_then_failure() {
        ResponseDto responseDto = ResponseDto.builder().build();

        ResponseEntity<ResponseDto> responseEntity = new ResponseEntity<>(
                responseDto, HttpStatus.OK
        );

        Mockito.doReturn(responseEntity).when(restTemplate)
                .getForEntity(
                        anyString(), any()
                );

        Country clientResponse = countryInfoRestClient.findCountryByIsoCode("XX");

        assertNull(clientResponse.getIsoCode(), "country code must have a value");
        assertNull(clientResponse.getName(), "country name must have a value");
        assertNull(clientResponse.getCurrencies(), "country must have at lkeast one currency");

    }

}
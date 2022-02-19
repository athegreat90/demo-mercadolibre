package com.mercadolibre.mercadolibrecountry.sources.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCountryDetail;
import com.mercadolibre.mercadolibrecountry.sources.redis.ip.api.RedisIpLocation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import redis.embedded.RedisServer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RedisClientTest
{
    @Autowired
    private RedisClient redisClient;

    @SpyBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ValueOperations valueOperations;

    @Test
    void when_IpLocation_save_redis_then_success()
    {
        saveMockRedis();


        IpAddress ipAddressModel = IpAddress.builder()
                .address("8.8.8.8")
                .country(Country.builder().isoCode("CO").name("Colombia").build())
                .build();

        assertDoesNotThrow(() -> redisClient.saveIpLocation(ipAddressModel));


    }

    private void saveMockRedis() {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();
        doReturn(Boolean.TRUE).when(redisTemplate).expire(any(), any());
        // This will make sure the actual method get is not called and mocked value is returned
        doNothing().when(valueOperations).set(anyString(), any());
    }

    @Test
    void when_IpLocation_findById_then_success() {

        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        var redisIpLocation = new RedisIpLocation();
        redisIpLocation.setIpAddress("8.8.8.8");
        redisIpLocation.setCountryCode("US");
        redisIpLocation.setCountryName("USA");
        doReturn(getJson(redisIpLocation)).when(valueOperations).get(anyString());

        var country  = redisClient.findIpLocation("8.8.8.8");

        assertNotNull(country);
        assertNotNull(country.getIsoCode());
    }

    @Test
    void when_IpLocation_findById_then_success_empty() {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        doReturn(null).when(valueOperations).get(anyString());
        var country  = redisClient.findIpLocation("8.8.8.8");

        assertNull(country);
    }


    @Test
    void when_RedisCountry_save_then_success()
    {
        saveMockRedis();

        List<Currency> currencies = new ArrayList<>();

        currencies.add( Currency.builder()
                .code("USD").name("US Dollar").build()
        );

        Country country = Country.builder()
                .isoCode("USA").name("United states of america")
                .currencies(currencies)
                .build();

        assertDoesNotThrow(() -> redisClient.saveCountryDetail(country));
    }

    @Test
    void when_RedisCountry_findById_then_success() {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        var jsonRaw = new Country( "United states of america","USA", List.of(new Currency("USD", "DOLLAR", "$", 0.0D, 0.0D)));
        doReturn(getJson(jsonRaw)).when(valueOperations).get(anyString());

        var country = redisClient.findCountryDetailByCode("USA");
        System.out.println(country);
        assertEquals("USA", country.getIsoCode() );
        assertEquals("United states of america", country.getName());
        assertEquals(1, country.getCurrencies().size() );
    }

    private String getJson(Object o)
    {
        try {
            var objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Test
    void when_RedisCountry_findById_then_null() {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        doReturn(null).when(valueOperations).get(anyString());
        Country country = redisClient.findCountryDetailByCode("USA");
        assertNull(country);
    }


    @Test
    void when_transformation_then_ok() throws JsonProcessingException {
        var jsonRaw = "{\"isoCode\":\"US\",\"name\":\"United States of America\",\"currencies\":[{\"code\":\"USD\",\"name\":\"United States dollar\",\"symbol\":\"$\",\"usdRate\":0.0,\"eurosRate\":0.0}]}";

        var objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        var jsonConverted = objectMapper.readValue(jsonRaw, new TypeReference<RedisCountryDetail>() {});
        assertNotNull(jsonConverted);
    }

    @Test
    void when_saveIpLocation_fail()
    {
//        doThrow(new RuntimeException()).when(redisTemplate).expire(anyString(), any());

//        given(redisTemplate.expire(anyString(), any())).willThrow(new RuntimeException());

        assertDoesNotThrow(() -> redisClient.saveIpLocation(null));
    }

    @Test
    void when_findIp_fails()
    {
        var response = redisClient.findIpLocation(null);
        assertNull(response);
    }

    @Test
    void when_saveCountryDetail_fail()
    {
        assertDoesNotThrow(() -> redisClient.saveCountryDetail(null));
    }

    @Test
    void when_findCountryDetailByCode_fails()
    {
        var response = redisClient.findCountryDetailByCode(null);
        assertNull(response);
    }

    @Test
    void when_saveCurrencyRate_fail()
    {
        assertThrows(RedisException.class, () -> redisClient.saveCurrencyRate(null, null, null));
    }

    @Test
    void when_saveCurrencyRate_ok()
    {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        doNothing().when(valueOperations).set(anyString(), any());
        doReturn(Boolean.TRUE).when(redisTemplate).expire(anyString(), any());

        assertDoesNotThrow(() -> redisClient.saveCurrencyRate("USD", "COP", 3800.0D));
    }

    @Test
    void when_findCurrencyRate_fails()
    {
        var response = redisClient.findCurrencyRate(null, null);
        assertNull(response);
    }

    @Test
    void when_findCurrencyRate_return_data()
    {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        doReturn(1.0D).when(valueOperations).get(anyString());
        var response = redisClient.findCurrencyRate("USD", "COP");
        assertEquals(1.0D, response);
    }

    @Test
    void when_findCurrencyRate_return_null()
    {
        // This will make sure the actual method opsForValue is not called and mocked valueOperations is returned
        doReturn(valueOperations).when(redisTemplate).opsForValue();

        // This will make sure the actual method get is not called and mocked value is returned
        doReturn(null).when(valueOperations).get(anyString());
        var response = redisClient.findCurrencyRate("USD", "COP");
        assertNull(response);
    }

}

package com.mercadolibre.mercadolibrecountry.sources.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCountryDetail;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCountryMapper;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCurrencyMapper;
import com.mercadolibre.mercadolibrecountry.sources.redis.ip.api.RedisIpAddressMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestRedisConfiguration.class)
@ActiveProfiles("test")
class RedisClientTest
{
    @Autowired
    private RedisClient redisClient;

    @SpyBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ValueOperations valueOperations;

    private RedisServer redisServer;

    @BeforeEach
    public void setUp()
    {
        try
        {
            redisServer = RedisServer.builder().port(6370).build();
            redisServer.start();
        }
        catch (Exception e)
        {
            //do nothing
        }
    }

    @AfterEach
    public void tearDown()
    {
        redisServer.stop();
    }


    @Test
    void when_IpLocation_save_redis_then_success()
    {
        IpAddress ipAddressModel = IpAddress.builder()
                .address("8.8.8.8")
                .country(Country.builder().isoCode("CO").name("Colombia").build())
                .build();

        assertDoesNotThrow(() -> redisClient.saveIpLocation(ipAddressModel));


    }

    @Test
    void when_IpLocation_findById_then_success() {

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
    void when_RedisCountry_save_then_success() {
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
        doReturn(new Country( "United states of america","USA", List.of(new Currency()))).when(valueOperations).get(anyString());

        Country country = redisClient.findCountryDetailByCode("USA");
        assertEquals("USA", country.getIsoCode() );
        assertEquals("United states of america", country.getName());
        assertEquals(1, country.getCurrencies().size() );
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

package com.mercadolibre.mercadolibrecountry.sources.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCountryDetail;
import com.mercadolibre.mercadolibrecountry.sources.redis.country.info.RedisCountryMapper;
import com.mercadolibre.mercadolibrecountry.sources.redis.ip.api.RedisIpAddressMapper;
import com.mercadolibre.mercadolibrecountry.sources.redis.ip.api.RedisIpLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Log4j2
@Component
@RequiredArgsConstructor
public class RedisClient
{
    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisIpAddressMapper redisIpAddressMapper;
    private final RedisCountryMapper redisCountryMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init()
    {
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    public void saveIpLocation(IpAddress ipAddress)
    {
        try
        {
            var redisIpLocation = redisIpAddressMapper.ipAddressToIpCountryInfo(ipAddress);
            redisTemplate.opsForValue().set(redisIpLocation.getIpAddress(), objectMapper.writeValueAsString(redisIpLocation));
            redisTemplate.expire(redisIpLocation.getIpAddress(), Duration.ofMillis(1296000L));
        }
        catch (Exception e)
        {
            log.error("saveIpLocation error:", e);
        }
    }

    public Country findIpLocation(String ipAddress)
    {
        try
        {
            if (!StringUtils.hasText(ipAddress))
            {
                throw new RedisException("The ip is empty");
            }
            var ipLocation = redisTemplate.opsForValue().get(ipAddress);
            log.info("findIpLocation: {}", String.valueOf(ipLocation));
            return ipLocation == null ? null : redisIpAddressMapper.ipCountryInfoToIpAddress(objectMapper.readValue(String.valueOf(ipLocation), new TypeReference<RedisIpLocation>() {})).getCountry();
        }
        catch (Exception e)
        {
            log.error("Getting ip location", e);
            return null;
        }
    }

    public void saveCountryDetail(Country country)
    {
        try
        {
            var redisCountryDetail = redisCountryMapper.countryToRedisCountry(country);
            log.info("Save Key =  {}", redisCountryDetail.getIsoCode());
            if (!StringUtils.hasText(redisCountryDetail.getIsoCode()))
            {
                return;
            }
            redisTemplate.opsForValue().set(redisCountryDetail.getIsoCode(), objectMapper.writeValueAsString(redisCountryDetail));
        }
        catch (Exception e)
        {
            log.error("saveIpLocation error:", e);
        }
    }

    public Country findCountryDetailByCode(String countryCode)
    {
        try
        {
            if (!StringUtils.hasText(countryCode))
            {
                throw new RedisException("The country code is empty");
            }
            var redisCountryDetail =  this.redisTemplate.opsForValue().get(countryCode);
            if (redisCountryDetail == null)
            {
                return null;
            }
            var raw = objectMapper.readValue(String.valueOf(redisCountryDetail), new TypeReference<RedisCountryDetail>() {});
            return redisCountryMapper.redisCountryToCountry(raw);
        }
        catch (Exception e)
        {
            log.error("Getting details country", e);
            return null;
        }
    }

    public void saveCurrencyRate(String from, String to, Double value)
    {
        if (!StringUtils.hasText(from) || !StringUtils.hasText(to) || value == null)
        {
            throw new RedisException("Some data is empty");
        }
        var key = from + "_to_" + to;
        redisTemplate.opsForValue().set(key, String.valueOf(value));
        redisTemplate.expire(key, Duration.ofMillis(1296000L));

    }

    public Double findCurrencyRate(String from, String to)
    {
        try
        {
            if (!StringUtils.hasText(from) || !StringUtils.hasText(to))
            {
                throw new RedisException("Some data is empty");
            }
            var response = redisTemplate.opsForValue().get(from + "_to_" + to);
            log.info("findCurrencyRate -> {}", response);
            return  response == null ? null : Double.valueOf(String.valueOf(response));
        }
        catch (Exception e)
        {
            log.error("findCurrencyRate", e);
            return null;
        }
    }
}

package com.mercadolibre.mercadolibrecountry.sources.redis.country.info;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import org.mapstruct.Mapper;

@Mapper(uses = RedisCurrencyMapper.class, componentModel = "spring")
public interface RedisCountryMapper {

    Country redisCountryToCountry (RedisCountryDetail redisCountryDetail);

    RedisCountryDetail countryToRedisCountry (Country country);
}

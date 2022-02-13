package com.mercadolibre.mercadolibrecountry.sources.redis.country.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisCountryDetail
{
    private String isoCode;
    private String name;
    private List<RedisCurrency> currencies;

}

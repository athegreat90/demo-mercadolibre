package com.mercadolibre.mercadolibrecountry.sources.redis.country.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisCurrency {

    private String code;
    private String name;
    private String symbol;
    private double usdRate;
    private double eurosRate;
}

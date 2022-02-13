package com.mercadolibre.mercadolibrecountry.sources.redis.currency.fixer;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash(value = "RedisCurrencyRate", timeToLive = 3600) // 1 hour
public class RedisCurrencyRate {
    @Id
    String convertionName;
    Double value;
}

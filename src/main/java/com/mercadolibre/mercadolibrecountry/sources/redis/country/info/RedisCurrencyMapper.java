package com.mercadolibre.mercadolibrecountry.sources.redis.country.info;

import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface RedisCurrencyMapper {

    Currency redisCurrencyToCurrency (RedisCurrency redisCurrency);
    RedisCurrency currencyToRedisCurrency (Currency currency);
}

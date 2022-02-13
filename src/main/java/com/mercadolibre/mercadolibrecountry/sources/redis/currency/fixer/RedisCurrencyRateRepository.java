package com.mercadolibre.mercadolibrecountry.sources.redis.currency.fixer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisCurrencyRateRepository extends CrudRepository<RedisCurrencyRate, String> {
}

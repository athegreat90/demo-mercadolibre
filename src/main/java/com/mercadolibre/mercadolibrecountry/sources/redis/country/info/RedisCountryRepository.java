package com.mercadolibre.mercadolibrecountry.sources.redis.country.info;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisCountryRepository extends CrudRepository<RedisCountryDetail, String> {
}

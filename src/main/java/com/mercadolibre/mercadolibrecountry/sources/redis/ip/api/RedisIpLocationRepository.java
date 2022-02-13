package com.mercadolibre.mercadolibrecountry.sources.redis.ip.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisIpLocationRepository extends CrudRepository<RedisIpLocation, String> {

}

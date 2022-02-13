package com.mercadolibre.mercadolibrecountry.sources.redis.ip.api;

import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface RedisIpAddressMapper {

    @Mapping(source = "address", target = "ipAddress")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.isoCode", target = "countryCode")
    RedisIpLocation ipAddressToIpCountryInfo(IpAddress ipAddress);

    @Mapping(source = "ipAddress", target = "address")
    @Mapping(source = "countryName", target = "country.name")
    @Mapping(source = "countryCode", target = "country.isoCode")
    IpAddress ipCountryInfoToIpAddress (RedisIpLocation redisIpLocation);
}

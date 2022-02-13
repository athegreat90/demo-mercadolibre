package com.mercadolibre.mercadolibrecountry.application.response;

import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import org.mapstruct.Mapper;

@Mapper(uses = CountryMapper.class, componentModel = "spring")
public interface IpAddressMapper {
    IpAddressResponseDto ipAddressToResponseDto(IpAddress ipAddress);
}

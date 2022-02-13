package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CountryMapper {

    @Mapping(source = "countryName", target = "name")
    @Mapping(source = "countryCode", target = "isoCode")
    Country responseDtoToCountry(ResponseDto responseDto);

}

package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {CurrencyMapper.class})
public interface CountryMapper {

    @Mapping(source = "alpha2Code", target = "isoCode")
    Country responseDtoToCountry (ResponseDto responseDto);
}

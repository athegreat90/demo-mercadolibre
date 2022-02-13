package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import com.mercadolibre.mercadolibrecountry.domain.model.Currency;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {

    //automatic, fields have the same name
    Currency currencyResponseDtoToCurrency(CurrencyResponseDto responseDto);
}

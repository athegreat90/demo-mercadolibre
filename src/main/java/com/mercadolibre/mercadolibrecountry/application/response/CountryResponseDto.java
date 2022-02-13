package com.mercadolibre.mercadolibrecountry.application.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class CountryResponseDto {

    private String name;
    private String isoCode;
    private List<CurrencyResponseDto> currencies;
}

package com.mercadolibre.mercadolibrecountry.application.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class CurrencyResponseDto {

    String code;
    String name;
    String symbol;
    double usdRate;
    double eurosRate;
}

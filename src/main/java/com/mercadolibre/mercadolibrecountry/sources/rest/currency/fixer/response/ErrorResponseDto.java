package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class ErrorResponseDto {
    int code;
    String type;
    String info;
}

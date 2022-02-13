package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class ResponseDto {
    boolean success;
    QueryResponseDto query;
    InfoResponseDto info;
    String date;
    double result;
    ErrorResponseDto  error;
}

package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class QueryResponseDto {
    String from;
    String to;
    int amount;
}

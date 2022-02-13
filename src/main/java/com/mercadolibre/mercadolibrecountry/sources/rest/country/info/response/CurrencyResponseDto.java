package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {
    String code;
    String name;
    String symbol;
}

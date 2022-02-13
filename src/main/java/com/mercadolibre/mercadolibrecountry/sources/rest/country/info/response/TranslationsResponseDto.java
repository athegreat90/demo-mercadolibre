package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class TranslationsResponseDto {
    String de;
    String es;
    String fr;
    String ja;
    String it;
    String br;
    String pt;
    String nl;
    String hr;
    String fa;
}

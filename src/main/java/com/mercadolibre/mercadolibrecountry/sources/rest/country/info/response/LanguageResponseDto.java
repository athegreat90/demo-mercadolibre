package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class LanguageResponseDto {

    @JsonProperty(value = "iso639_1")
    String iso6391;

    @JsonProperty(value = "iso639_2")
    String iso6392;
    String name;
    String nativeName;
}

package com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class RegionalBlocResponseDto {
    String acronym;
    String name;
    List<String> otherAcronyms;
    List<String> otherNames;
}

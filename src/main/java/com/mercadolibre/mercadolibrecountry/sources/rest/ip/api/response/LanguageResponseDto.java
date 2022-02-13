package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Jacksonized
@Builder
@Value
public class LanguageResponseDto {

    String code;
    String name;

}

package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Value
public class LocationResponseDto {

    @JsonProperty(value = "geoname_id")
    Integer geoNameId;

    String capital;

    List<LanguageResponseDto> languages;

    @JsonProperty(value = "country_flag")
    String countryFlag;

    @JsonProperty(value = "country_flag_emoji")
    String countryFlagEmoji;

    @JsonProperty(value = "country_flag_emoji_unicode")
    String countryFlagEmojiUnicode;

    @JsonProperty(value = "calling_code")
    String callingCode;

    @JsonProperty(value = "is_eu")
    Boolean isEu;


}

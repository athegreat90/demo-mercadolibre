package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
@JsonIgnoreProperties
public class ResponseDto {
    String ip;
    String type;

    @JsonProperty(value = "continent_code")
    String continentCode;

    @JsonProperty(value = "continent_name")
    String continentName;

    @JsonProperty(value = "country_code")
    String countryCode;

    @JsonProperty(value = "country_name")
    String countryName;

    @JsonProperty(value = "region_code")
    String regionCode;

    @JsonProperty(value = "region_name")
    String regionName;

    String city;

    Object zip;

    double latitude;

    double longitude;

    LocationResponseDto location;

}

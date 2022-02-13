package com.mercadolibre.mercadolibrecountry.application.response;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
@Data
public class IpAddressResponseDto {

    private String address;
    private CountryResponseDto country ;

}

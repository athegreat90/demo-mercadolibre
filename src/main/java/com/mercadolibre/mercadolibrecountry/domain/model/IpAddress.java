package com.mercadolibre.mercadolibrecountry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IpAddress {

    private String address;
    private Country country ;
}

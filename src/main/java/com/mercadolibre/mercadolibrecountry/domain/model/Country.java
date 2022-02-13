package com.mercadolibre.mercadolibrecountry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    private String name;
    private String isoCode;
    private List<Currency> currencies;


}

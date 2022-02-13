package com.mercadolibre.mercadolibrecountry.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    private String code;
    private String name;
    private String symbol;
    private double usdRate;
    private double eurosRate;
}

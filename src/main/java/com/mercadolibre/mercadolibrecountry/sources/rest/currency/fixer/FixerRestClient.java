package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log4j2
public class FixerRestClient {

    @Value("${com.mercadolibre.rest.fixer.accessKey:dummyKey}")
    private String ipApiAccessKey;

    private final FixerFeignClient feignClient;

    public Double findRate(String fromCurrency, String toCurrency){

        var responseDto = this.feignClient.get(ipApiAccessKey, fromCurrency, toCurrency, 1, 1);
        if (responseDto == null)
        {
            return 0.0D;
        }
        return responseDto.getResult();
    }
}

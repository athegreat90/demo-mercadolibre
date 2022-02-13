package com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer;

import com.mercadolibre.mercadolibrecountry.sources.rest.currency.fixer.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fixer-api", url = "${com.mercadolibre.rest.fixer.url:dummyUrl}")
public interface FixerFeignClient
{
    @GetMapping("")
    ResponseDto get(
            @RequestParam("access_key") String accessKey,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "format") Integer format
    );
}

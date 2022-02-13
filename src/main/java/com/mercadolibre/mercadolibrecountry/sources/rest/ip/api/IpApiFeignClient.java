package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api;

import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ip-api", url = "${com.mercadolibre.rest.ip.api.url:dummyUrl}")
public interface IpApiFeignClient
{
    @GetMapping("/{userIp}/")
    ResponseDto get(@PathVariable(name = "userIp") String userIp,
                    @RequestParam(name = "access_key") String accessKey,
                    @RequestParam(name = "format") Integer format);
}


// http://api.ipapi.com/mercadolibre.com?access_key=8a3269392e43266fe46882040ca00c12
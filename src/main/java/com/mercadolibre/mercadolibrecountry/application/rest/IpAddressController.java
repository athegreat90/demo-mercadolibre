package com.mercadolibre.mercadolibrecountry.application.rest;


import com.mercadolibre.mercadolibrecountry.application.response.IpAddressMapper;
import com.mercadolibre.mercadolibrecountry.application.response.IpAddressResponseDto;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.domain.service.DomainIpAddressService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IpAddressController {

    private final DomainIpAddressService domainIpAddressService;
    private final IpAddressMapper ipAddressMapper;


    @GetMapping("/getIpAddress")
    public IpAddressResponseDto findIpAddress (@RequestParam String ip) {
        IpAddress ipAddress = domainIpAddressService.findIpAddress(ip);
        return this.ipAddressMapper.ipAddressToResponseDto(ipAddress) ;
    }
}

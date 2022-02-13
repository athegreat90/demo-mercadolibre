package com.mercadolibre.mercadolibrecountry.domain.service;

import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import org.springframework.stereotype.Service;

@Service
public interface IpAddressService {

    IpAddress findIpAddress(String ipAddress);

}

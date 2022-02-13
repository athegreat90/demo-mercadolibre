package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response.CountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class IpApiRestClient
{

    public static final int FORMAT = 1;

    @Value("${com.mercadolibre.rest.ip.api.accessKey:dummyKey}")
    private String ipApiAccessKey;

    private final IpApiFeignClient restClient;

    private CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    public Country findCountryByIp(String ipAddress)
    {
        log.info("Country IP: {}", ipAddress);
        log.info("Country Access Key: {}", ipApiAccessKey);
        var response = this.restClient.get(ipAddress, this.ipApiAccessKey, FORMAT);
        return countryMapper.responseDtoToCountry(response);
    }
}

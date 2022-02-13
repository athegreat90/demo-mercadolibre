package com.mercadolibre.mercadolibrecountry.sources.rest.country.info;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response.ResponseDto;
import com.mercadolibre.mercadolibrecountry.sources.rest.country.info.response.CountryMapper;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
@Log4j2
public class CountryInfoRestClient {

    private RestTemplate restTemplate;
    @Value("${com.mercadolibre.rest.countr.info.url:dummyUrl}")
    private String apiUrl;

    private CountryMapper countryMapper;


    public CountryInfoRestClient() {
        countryMapper = Mappers.getMapper(CountryMapper.class);
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init()
    {
        log.info("Prop: {}", this.apiUrl);
    }

    public Country findCountryByIsoCode(String isoCode){
        String finalUrl = String.format("%s/%s", this.apiUrl, isoCode );

        ResponseEntity<ResponseDto> responseEntity =  this.restTemplate.getForEntity(finalUrl, ResponseDto.class);
        return countryMapper.responseDtoToCountry(responseEntity.getBody());
    }

}

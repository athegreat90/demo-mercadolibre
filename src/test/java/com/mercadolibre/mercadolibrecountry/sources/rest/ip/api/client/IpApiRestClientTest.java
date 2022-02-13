package com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.client;

import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.IpApiFeignClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.IpApiRestClient;
import com.mercadolibre.mercadolibrecountry.sources.rest.ip.api.response.ResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IpApiRestClientTest {

    @Autowired
    private IpApiRestClient ipApiRestClient;

    @MockBean
    private IpApiFeignClient feignClient;

    @Test
    void when_ip_is_public_then_success()
    {
        ResponseDto responseDto = ResponseDto.builder()
                .countryName("United States")
                .countryCode("US").build();

        doReturn(responseDto).when(feignClient).get(anyString(), anyString(), any());

        Country clientResponse = ipApiRestClient.findCountryByIp("8.8.8.8");

        assertNotNull(clientResponse.getIsoCode(), "country code must have a value");
        assertNotNull(clientResponse.getName(), "country name must have a value");
    }


    @Test
    void when_ip_is_private_then_faliure() {
        ResponseDto responseDto = ResponseDto.builder()
                .build();

        doReturn(responseDto).when(feignClient).get(anyString(), anyString(), any());

        Country clientResponse = ipApiRestClient.findCountryByIp("127.0.0.1");

        assertNull(clientResponse.getIsoCode(), "country code must have a value");
        assertNull(clientResponse.getName(), "country name must have a value");
    }
}
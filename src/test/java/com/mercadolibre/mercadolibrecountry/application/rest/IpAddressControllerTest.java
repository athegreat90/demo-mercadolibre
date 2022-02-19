package com.mercadolibre.mercadolibrecountry.application.rest;

import com.mercadolibre.mercadolibrecountry.MercadoLibreCountryApplication;
import com.mercadolibre.mercadolibrecountry.domain.model.Country;
import com.mercadolibre.mercadolibrecountry.domain.model.IpAddress;
import com.mercadolibre.mercadolibrecountry.domain.service.DomainIpAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class IpAddressControllerTest {

    @Autowired
    private IpAddressController ipAddressController;

    @MockBean
    private DomainIpAddressService domainIpAddressService;


    @Test
    void contextLoads() throws Exception {

//        var ip = IpAddress.builder().address("1.1.1.1").country(Country.builder().build()).build();
        var ip = new IpAddress("1.1.1.1", new Country());
        when(domainIpAddressService.findIpAddress("1.1.1.1")).thenReturn(ip);
        var response = this.ipAddressController.findIpAddress("1.1.1.1");
        assertEquals("1.1.1.1", response.getAddress());
    }

}
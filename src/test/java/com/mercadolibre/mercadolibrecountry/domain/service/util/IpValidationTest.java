package com.mercadolibre.mercadolibrecountry.domain.service.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.mercadolibre.mercadolibrecountry.domain.service.util.IpValidation.isPublicIp;
import static org.junit.jupiter.api.Assertions.*;

class IpValidationTest {


    @Test
    void when_localAddresses_should_fail() {
        List<String> addresses = new ArrayList<>();
        addresses.add("192.168.0.1");
        addresses.add("192.168.100.56");
        addresses.add("172.23.56.89");
        addresses.add("127.0.0.1");

        for (String address : addresses) {
            assertFalse(isPublicIp(address), "It's a public ip");
        }
    }


    @Test
    void when_publicAddresses_should_succeed()
    {
        List<String> addresses = new ArrayList<>();
        addresses.add("8.8.8.8"); //google usa dns
        addresses.add("1.1.1.1"); //cloudfare au dns

        for (String address : addresses) {
            assertTrue(isPublicIp(address), "It's a private ip");
        }
    }

    @Test
    void when_publicIp_should_failed()
    {
        assertFalse(isPublicIp("[]"));
    }

}
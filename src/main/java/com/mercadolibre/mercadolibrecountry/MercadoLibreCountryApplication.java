package com.mercadolibre.mercadolibrecountry;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
@EnableFeignClients("com.mercadolibre.mercadolibrecountry")
public class MercadoLibreCountryApplication {

    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(MercadoLibreCountryApplication.class, args);
    }
}

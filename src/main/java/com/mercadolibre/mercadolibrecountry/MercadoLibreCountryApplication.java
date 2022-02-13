package com.mercadolibre.mercadolibrecountry;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Log4j2
@SpringBootApplication
@RequiredArgsConstructor
@EnableFeignClients("com.mercadolibre.mercadolibrecountry")
public class MercadoLibreCountryApplication {

    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(MercadoLibreCountryApplication.class, args);
    }

    @PostConstruct
    public void init()
    {
        log.info("com.mercadolibre.redis.host = {}", env.getProperty("com.mercadolibre.redis.host"));
        log.info("com.mercadolibre.redis.port = {}", env.getProperty("com.mercadolibre.redis.port"));
        log.info("com.mercadolibre.rest.countr.info.url = {}", env.getProperty("com.mercadolibre.rest.countr.info.url"));
        log.info("com.mercadolibre.rest.fixer.url = {}", env.getProperty("com.mercadolibre.rest.fixer.url"));
        log.info("com.mercadolibre.rest.fixer.accessKey = {}", env.getProperty("com.mercadolibre.rest.fixer.accessKey"));
        log.info("com.mercadolibre.rest.ip.api.accessKey = {}", env.getProperty("com.mercadolibre.rest.ip.api.accessKey"));
        log.info("com.mercadolibre.rest.ip.api.url = {}", env.getProperty("com.mercadolibre.rest.ip.api.url"));
    }
}

package com.mercadolibre.mercadolibrecountry.sources.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration
{
    private RedisServer redisServer;

    public TestRedisConfiguration() {
        this.redisServer = RedisServer.builder().port(6379).setting("maxheap 2gb").build();
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}

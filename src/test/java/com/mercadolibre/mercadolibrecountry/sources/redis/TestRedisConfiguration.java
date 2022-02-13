package com.mercadolibre.mercadolibrecountry.sources.redis;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;

@TestConfiguration
public class TestRedisConfiguration
{
    private RedisServer redisServer;

    public TestRedisConfiguration() {
        this.redisServer = RedisServer.builder().port(new Random().nextInt(7000)).setting("maxheap 2gb").setting("timeout 30000").build();
    }

    @PostConstruct
    public void postConstruct() {
        if (redisServer.isActive())
        {
            return;
        }
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        if (!redisServer.isActive())
        {
            return;
        }
        redisServer.stop();
    }
}

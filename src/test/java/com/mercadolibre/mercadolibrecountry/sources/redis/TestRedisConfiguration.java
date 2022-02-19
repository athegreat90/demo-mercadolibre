package com.mercadolibre.mercadolibrecountry.sources.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@TestConfiguration
public class TestRedisConfiguration
{

    private RedisServer redisServer;

    public TestRedisConfiguration(RedisProperties redisProperties) {
//        this.redisServer = RedisServer.builder().port(redisProperties.getPort()).setting("maxheap 2gb").setting("timeout 30000").build();
        if (redisServer != null && redisServer.isActive())
        {
            return;
        }
        this.redisServer = new RedisServer(ThreadLocalRandom.current().nextInt(65000));
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

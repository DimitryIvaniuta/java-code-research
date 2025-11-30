package com.code.research.springboot.profile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.redisson.api.RedissonClient")
public class RedisCacheConfig {

    @Bean
    public CacheManager redisCacheManager(RedissonClient client) {
        // only created if Redisson is on classpath
        return new RedisCacheManager(client);
    }

    @Bean
    public RedissonClient redissonClient() {
        return new InMemoryRedissonClient(); // replace with real RedissonClient in prod
    }

}

package net.cglcapital.coininfo.common.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class RedisHealthCheck implements HealthCheck {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Health check() {
        Health health = new Health();
        health.setName("Redis");
        health.setStatus(HealthStatus.UNHEALTHY);

        RedisConnection connection = null;
        try {
            connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();

            if ("PONG".equals(connection.ping())) {
                health.setStatus(HealthStatus.HEALTHY);
            }
        } catch (Exception e) {
            log.warn("[RedisHealthCheck] Redis exception: {}", e.getMessage());
        } finally {
            if (connection != null) RedisConnectionUtils.releaseConnection(connection, redisTemplate.getConnectionFactory());
        }

        return health;
    }
}

package net.cglcapital.coininfo.api.service;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.service.HealthCheck;
import net.cglcapital.coininfo.consumer.health.EventListenerHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BinanceWebSocketHealthCheck implements HealthCheck {

    @Autowired
    private EventListenerHealth eventListenerHealth;

    @Override
    public Health check() {
        Health health = new Health();
        health.setName("EventStreaming");
        health.setStatus(HealthStatus.HEALTHY);

        if (eventListenerHealth.getListenerHealthMap().containsValue(false)) {
            health.setStatus(HealthStatus.UNHEALTHY);
        }

        return health;
    }
}

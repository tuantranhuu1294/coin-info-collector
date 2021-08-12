package net.cglcapital.coininfo.api.service;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.common.api.service.HealthCheck;
import net.cglcapital.coininfo.common.api.service.RedisHealthCheck;
import net.cglcapital.coininfo.common.model.response.HealthCheckResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static net.cglcapital.coininfo.common.api.service.HealthCheck.HealthStatus.HEALTHY;
import static net.cglcapital.coininfo.common.api.service.HealthCheck.HealthStatus.UNHEALTHY;

@Slf4j
@Service
public class HealthCheckService {

    @Autowired
    private RedisHealthCheck redisHealthCheck;

    public ResponseEntity<Object> healthCheck() {
        HealthCheckResponse response = new HealthCheckResponse();
        String version = System.getenv("VERSION");
        if (StringUtils.isBlank(version)) {
            version = "1.0.0";
        }
        response.setVersion(version);

        List<HealthCheck.Health> data = new ArrayList<>();
        data.add(redisHealthCheck.check());

        boolean isUnhealthy = data
            .stream()
            .anyMatch(health -> health.getStatus().equals(UNHEALTHY));
        response.setStatus(isUnhealthy ? UNHEALTHY : HEALTHY);

        return (response.getStatus().equals(HEALTHY)) ? ResponseEntity.ok(response) :
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}

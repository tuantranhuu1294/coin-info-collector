package net.cglcapital.coininfo.common.api.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface HealthCheck {

    Health check();

    enum HealthStatus {
        HEALTHY, UNHEALTHY
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Health {
        private String name;
        private HealthStatus status;
        private String instance;
    }
}

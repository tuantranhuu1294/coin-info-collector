package net.cglcapital.coininfo.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.cglcapital.coininfo.common.api.service.HealthCheck;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthCheckResponse {

    private String version;
    private HealthCheck.HealthStatus status;
}

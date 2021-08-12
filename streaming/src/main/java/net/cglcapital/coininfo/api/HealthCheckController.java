package net.cglcapital.coininfo.api;

import lombok.extern.slf4j.Slf4j;
import net.cglcapital.coininfo.api.service.HealthCheckService;
import net.cglcapital.coininfo.common.api.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController extends BaseController {

    @Autowired
    private HealthCheckService healthCheckService;

    @GetMapping("/version")
    public ResponseEntity<Object> version() {
        String version = System.getenv("VERSION");
        if (StringUtils.isBlank(version)) {
            version = "1.0.0";
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>("coin-info-streaming " + version, header, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return healthCheckService.healthCheck();
    }

}

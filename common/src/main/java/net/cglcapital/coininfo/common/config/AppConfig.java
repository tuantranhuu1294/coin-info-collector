package net.cglcapital.coininfo.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Slf4j
@ComponentScan("net.cglcapital.coininfo.common.db")
@EnableJpaRepositories(basePackages = "net.cglcapital.coininfo.common.db")
@EnableScheduling
@EnableJpaAuditing
@Configuration
public class AppConfig {

    @Autowired
    private Environment environment;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;
    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maximumPoolSize;
    @Value("${spring.datasource.hikari.minimum-idle}")
    private int minimumIdle;
    @Value("${httpclient.connect-timeout}")
    private int HTTP_CONNECT_TIMEOUT;
    @Value("${httpclient.read-timeout}")
    private int HTTP_READ_TIMEOUT;

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        objectMapper.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
        factory.setReadTimeout(HTTP_READ_TIMEOUT);

        return new RestTemplate(factory);
    }

    @Bean
    public DataSource coinInfoDataSource() throws IllegalStateException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(dbUserName);
        hikariConfig.setPassword(environment.getProperty("spring.datasource.password"));
        hikariConfig.setDriverClassName(dbDriverClassName);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setMinimumIdle(minimumIdle);

        // add specific default config
        hikariConfig.setPoolName("coin-info-db-pool");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");

        return new HikariDataSource(hikariConfig);
    }
}

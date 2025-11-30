package com.code.research.springboot.profile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    @ConditionalOnProperty(
            name = "app.metrics.enabled",
            havingValue = "true",
            matchIfMissing = false
    )
    public MetricsExporter metricsExporter() {
        return new MetricsExporter();
    }
}

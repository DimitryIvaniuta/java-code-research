package com.code.research.springboot.profile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionalOnExpressionConfiguration {
    @Bean
    @ConditionalOnExpression("'${app.mode}' == 'EXPERIMENTAL'")
    public FeatureToggle experimentalFeature() {
        return new FeatureToggle(true);
    }
}

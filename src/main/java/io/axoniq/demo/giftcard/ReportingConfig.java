package io.axoniq.demo.giftcard;

import io.axoniq.demo.giftcard.reporting.MyReportingInterceptor;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "reporting.enabled", havingValue = "true", matchIfMissing = false)
public class ReportingConfig {

    @Bean
    public MyReportingInterceptor reportingInterceptor(EventGateway eventGateway) {
        return new MyReportingInterceptor(eventGateway);
    }

    @Autowired
    public void configureReportingInterceptorFor(EventProcessingConfigurer eventProcessingConfigurer, MyReportingInterceptor reportingInterceptor) {
        eventProcessingConfigurer.registerHandlerInterceptor("card-summary", config -> reportingInterceptor);
    }
}

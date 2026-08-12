package com.foodorder.order.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient catalogRestClient(RestClient.Builder restClientBuilder,
                                         @Value("${catalog-service.base-url}") String baseUrl) {
        return restClientBuilder.baseUrl(baseUrl).build();
    }
}

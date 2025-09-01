package com.bff.bffservice.configs;

import com.bff.bffservice.constants.BaseUrls;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

    @Configuration
    public class WebClientConfig {
        @Bean
        public WebClient transactionServiceWebClient() {
            return WebClient.builder()
                    .baseUrl(BaseUrls.TRANSACTION_URL)
                    .build();
        }

        @Bean
        public WebClient userServiceWebClient() {
            return WebClient.builder()
                    .baseUrl(BaseUrls.USER_URL)
                    .build();
        }

        @Bean
        public WebClient accountServiceWebClient() {
            return WebClient.builder()
                    .baseUrl(BaseUrls.ACCOUNT_URL)
                    .build();
        }
    }

package com.bff.bffservice.utils;


import com.bff.bffservice.dto.AccountDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;


@Component
public class AccountClient {

    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8082";

    public AccountClient(@Qualifier("accountServiceWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<List<AccountDto>> getUserAccounts(UUID userId){
        return webClient.get()
                .uri("users/{userId}/accounts" , userId)
                .retrieve()
                .bodyToFlux(AccountDto.class)
                .collectList();
    }
}

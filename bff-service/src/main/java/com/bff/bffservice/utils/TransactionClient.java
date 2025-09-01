package com.bff.bffservice.utils;

import com.bff.bffservice.dto.AccountDto;
import com.bff.bffservice.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component

public class TransactionClient {

    private final WebClient webClient;
    private final String BASE_URL = "http://localhost:8087";

    public TransactionClient(@Qualifier("transactionServiceWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<List<TransactionDto>> getAccountTransactions(UUID accountId){
        return webClient.get()
                .uri("/accounts/{accountId}/transactions" , accountId)
                .retrieve()
                .bodyToFlux(TransactionDto.class)
                .collectList();
    }
}

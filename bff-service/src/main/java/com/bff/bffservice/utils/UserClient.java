package com.bff.bffservice.utils;

import com.bff.bffservice.dto.UserProfileResponse;
import com.bff.bffservice.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Qualifier("userServiceWebClient") WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<UserProfileResponse> getUserProfile(UUID userId){
        return webClient.get()
                .uri("/users/{userId}/profile", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError , response ->
                    Mono.error(new UserNotFoundException("User with Id " + userId + " Not Found"))
                )
                .onStatus(HttpStatusCode::is5xxServerError , response ->
                        Mono.error(new RuntimeException("Error in UserService"))
                )
                .bodyToMono(UserProfileResponse.class);
    }
}

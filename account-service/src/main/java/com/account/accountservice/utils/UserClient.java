package com.account.accountservice.utils;

import com.account.accountservice.exceptions.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class UserClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8081";

    public boolean checkIfUserExists(UUID userId){
        try {
            restTemplate.getForObject( BASE_URL + "/users/" + userId + "/profile", Object.class);
            return true;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("User with Id: " + userId + " not valid"); // or throw your own exception
        }
    }
}

package com.transaction.transactionservice.utils;

import com.transaction.transactionservice.dto.AccountDto;
import com.transaction.transactionservice.dto.TransferRequestDto;
import com.transaction.transactionservice.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8082";

    public AccountDto getAccount(UUID accountId){
        try {
            return restTemplate.getForObject( BASE_URL + "/accounts/" + accountId, AccountDto.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new AccountNotFoundException("Account with Id: " + accountId + " not valid"); // or throw your own exception
        }
    }

    public boolean transferAmount(UUID fromAccountId , UUID toAccountId , BigDecimal amount){
        try{
            TransferRequestDto req = TransferRequestDto.builder()
                    .fromAccountId(fromAccountId)
                    .toAccountId(toAccountId)
                    .amount(amount)
                    .build();

            restTemplate.put(BASE_URL + "/accounts/transfer" , req);

            return true;
        }
        catch(HttpClientErrorException ex){
            throw ex;
        }
    }
}

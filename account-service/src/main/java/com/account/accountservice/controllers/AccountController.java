package com.account.accountservice.controllers;

import com.account.accountservice.dto.AccountDto;
import com.account.accountservice.dto.CreateAccountReqDto;
import com.account.accountservice.dto.CreateAccountRespDto;
import com.account.accountservice.dto.TransferRequestDto;
import com.account.accountservice.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.account.accountservice.producer.LogProducer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping()
public class AccountController {

    @Autowired
    private AccountService accService;

    @Autowired
    private LogProducer logProducer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/accounts")
    public ResponseEntity<CreateAccountRespDto> addAccount(@RequestBody @Valid CreateAccountReqDto request){
        logAsJson(request, "Request");
        CreateAccountRespDto resp = accService.addAccount(request);
        logAsJson(resp, "Response");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDto>
    getAccount(@PathVariable @Valid UUID accountId){
        logAsJson(accountId, "Request");
        AccountDto resp = accService.getAccountById(accountId);
        logAsJson(resp, "Response");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsOfUser(@PathVariable UUID userId){
        logAsJson(userId, "Request");
        List<AccountDto> resp = accService.getAccountsByUserId(userId);
        logAsJson(resp, "Response");
        if(resp.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/accounts/transfer")
    public ResponseEntity<Map<String , String>> transferAmount(@RequestBody @Valid TransferRequestDto req){
        logAsJson(req, "Request");
        Map<String , String> resp = new HashMap<>();

        accService.transferAmount(req);

        resp.put("message" , "Accounts Updated Successfully");
        logAsJson(resp, "Response");
        return ResponseEntity.ok(resp);
    }

    private void logAsJson(Object obj, String type) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            logProducer.sendLog(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

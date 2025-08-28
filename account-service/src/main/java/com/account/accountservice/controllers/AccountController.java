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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping()
public class AccountController {

    @Autowired
    private AccountService accService;

    @PostMapping("/accounts")
    public ResponseEntity<CreateAccountRespDto> addAccount(@RequestBody @Valid CreateAccountReqDto request){
        CreateAccountRespDto resp = accService.addAccount(request);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDto>
    getAccount(@PathVariable @Valid UUID accountId){

        AccountDto resp = accService.getAccountById(accountId);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsOfUser(@PathVariable UUID userId){

        List<AccountDto> resp = accService.getAccountsByUserId(userId);

        if(resp.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resp);
    }

    @PutMapping("/accounts/transfer")
    public ResponseEntity<Map<String , String>> transferAmount(@RequestBody @Valid TransferRequestDto req){

        Map<String , String> resp = new HashMap<>();

        accService.transferAmount(req);

        resp.put("message" , "Accounts Updated Successfully");

        return ResponseEntity.ok(resp);
    }
}

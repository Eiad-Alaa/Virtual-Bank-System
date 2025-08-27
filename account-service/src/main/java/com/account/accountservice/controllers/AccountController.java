package com.account.accountservice.controllers;

import com.account.accountservice.dto.AccountDto;
import com.account.accountservice.dto.CreateAccountReqDto;
import com.account.accountservice.dto.CreateAccountRespDto;
import com.account.accountservice.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accService;

    @PostMapping
    public ResponseEntity<CreateAccountRespDto> addAccount(@RequestBody @Valid CreateAccountReqDto request){
        CreateAccountRespDto resp = accService.addAccount(request);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto>
    getAccount(@PathVariable @Valid UUID accountId){

        AccountDto resp = accService.getAccountById(accountId);

        return ResponseEntity.ok(resp);
    }
}

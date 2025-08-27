package com.account.accountservice.services;

import com.account.accountservice.dto.AccountDto;
import com.account.accountservice.dto.CreateAccountReqDto;
import com.account.accountservice.dto.CreateAccountRespDto;
import com.account.accountservice.entity.Account;
import com.account.accountservice.exceptions.AccountNotFoundException;
import com.account.accountservice.mappers.AccountMapper;
import com.account.accountservice.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private AccountMapper mapper;

    public AccountDto getAccountById(UUID accountId){

        Account acc = accountRepo.findById(accountId)
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with id " + accountId + " not found"));


        return mapper.toDto(acc);
    }

    @Transactional
    public CreateAccountRespDto addAccount(CreateAccountReqDto request){

        Account acc = mapper.createEntity(request);

        acc.setAccountId(UUID.randomUUID());
        acc.setAccountNum(generateRandomAccNumber(10));

        accountRepo.save(acc);

        CreateAccountRespDto resp = mapper.toCreateDto(acc);
        resp.setMessage("Account Created Successfully");

        return resp;
    }

    private String generateRandomAccNumber(int length){

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}

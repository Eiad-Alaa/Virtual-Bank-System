package com.account.accountservice.services;

import com.account.accountservice.dto.AccountDto;
import com.account.accountservice.dto.CreateAccountReqDto;
import com.account.accountservice.dto.CreateAccountRespDto;
import com.account.accountservice.dto.TransferRequestDto;
import com.account.accountservice.entity.Account;
import com.account.accountservice.entity.Transfer;
import com.account.accountservice.exceptions.AccountNotFoundException;
import com.account.accountservice.exceptions.InsufficientFundsException;
import com.account.accountservice.mappers.AccountMapper;
import com.account.accountservice.repositories.AccountRepository;
import com.account.accountservice.repositories.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepo;
    private final AccountMapper mapper;
    private final TransferRepository transferRepo;

    public AccountDto getAccountById(UUID accountId){

        Account acc = accountRepo.findById(accountId)
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with id " + accountId + " not found"));


        return mapper.toDto(acc);
    }

    public List<AccountDto> getAccountsByUserId(UUID userId){

        List<Account> accounts = accountRepo.findByUserId(userId);

        return accounts.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void transferAmount(TransferRequestDto req){
        
        Account fromAccount = accountRepo.findById(req.getFromAccountId())
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with Id " + req.getFromAccountId() + " not found")
                );

        Account toAccount = accountRepo.findById(req.getToAccountId())
                .orElseThrow(
                        () -> new AccountNotFoundException("Account with Id " + req.getToAccountId() + " not found")
                );

        if(req.getAmount().compareTo(fromAccount.getBalance()) > 0){
            throw new InsufficientFundsException("Account has insufficient funds");
        }

        Transfer transfer = Transfer.builder()
                .transactionId(UUID.randomUUID())
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(req.getAmount())
                .build();

        fromAccount.setBalance(fromAccount.getBalance().subtract(req.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(req.getAmount()));

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);
        transferRepo.save(transfer);
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

package com.account.accountservice.mappers;

import com.account.accountservice.constants.AccountStatus;
import com.account.accountservice.dto.AccountDto;
import com.account.accountservice.dto.CreateAccountReqDto;
import com.account.accountservice.dto.CreateAccountRespDto;
import com.account.accountservice.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account createEntity(CreateAccountReqDto req){
        Account newAcc = new Account();

        newAcc.setUserId(req.getUserId());
        newAcc.setAccountTypes(req.getAccountType());
        newAcc.setBalance(req.getInitialBalance());
        newAcc.setStatus(AccountStatus.ACTIVE);

        return newAcc;
    }

    public CreateAccountRespDto toCreateDto(Account acc){
        return CreateAccountRespDto.builder()
                        .accountId(acc.getAccountId())
                        .accountNumber(acc.getAccountNum())
                        .message("Account Created Successfully")
                        .build();

    }

    public AccountDto toDto(Account acc){
        return AccountDto.builder()
                .accountId(acc.getAccountId())
                .accountNum(acc.getAccountNum())
                .accountType(acc.getAccountTypes())
                .balance(acc.getBalance())
                .status(acc.getStatus())
                .build();
    }

}

package com.bff.bffservice.mappers;

import com.bff.bffservice.dto.AccountDto;
import com.bff.bffservice.dto.AccountRespDto;
import com.bff.bffservice.dto.TransactionDto;
import com.bff.bffservice.dto.TransactionsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountResponseMapper {

    public AccountRespDto toAccountResp
            (AccountDto accDto , List<TransactionsResponseDto> transactions){

        return AccountRespDto.builder()
                .accountId(accDto.getAccountId())
                .accountNumber(accDto.getAccountNum())
                .accountType(accDto.getAccountType())
                .balance(accDto.getBalance())
                .transcations(transactions)
                .build();
    }

}

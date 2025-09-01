package com.bff.bffservice.mappers;

import com.bff.bffservice.dto.TransactionDto;
import com.bff.bffservice.dto.TransactionsResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TransactionResponseMapper {

    public TransactionsResponseDto mapToResp(TransactionDto tx){
        return TransactionsResponseDto.builder()
                .transactionId(tx.getTransactionId())
                .amount(tx.getAmount())
                .fromAccount(tx.getFromAccount())
                .toAccountId(tx.getToAccount())
                .description(tx.getDescription())
                .timestamp(tx.getTimestamp())
                .build();
    }
}

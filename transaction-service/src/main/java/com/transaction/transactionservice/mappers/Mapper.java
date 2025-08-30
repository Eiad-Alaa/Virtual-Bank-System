package com.transaction.transactionservice.mappers;

import com.transaction.transactionservice.constants.TransactionStatus;
import com.transaction.transactionservice.dto.TransactionDto;
import com.transaction.transactionservice.dto.TransactionInitiationReqDto;
import com.transaction.transactionservice.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Mapper {

    public Transaction createEntity(TransactionInitiationReqDto req){
        return Transaction.builder()
                .fromAccount(req.getFromAccountId())
                .toAccount(req.getToAccountId())
                .amount(req.getAmount())
                .status(TransactionStatus.INITIATED)
                .description(req.getDescription())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public TransactionDto toEntity(Transaction transaction){
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .fromAccount(transaction.getFromAccount())
                .toAccount(transaction.getToAccount())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .description(transaction.getDescription())
                .status(transaction.getStatus().name())
                .build();
    }
}

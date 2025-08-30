package com.transaction.transactionservice.services;

import com.transaction.transactionservice.constants.TransactionStatus;
import com.transaction.transactionservice.dto.*;
import com.transaction.transactionservice.entity.Transaction;
import com.transaction.transactionservice.exceptions.AccountNotFoundException;
import com.transaction.transactionservice.exceptions.InsufficientFundsException;
import com.transaction.transactionservice.exceptions.TransactionNotFoundException;
import com.transaction.transactionservice.mappers.Mapper;
import com.transaction.transactionservice.repositories.TransactionRepository;
import com.transaction.transactionservice.utils.AccountClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final AccountClient accountClient;
    private final Mapper transactionMapper;

    @Transactional
    public TransactionRespDto initiateTransaction
            (TransactionInitiationReqDto req){

        Transaction transaction = transactionMapper.createEntity(req);

        transaction.setTransactionId(UUID.randomUUID());

        Transaction savedTrans = transactionRepo.save(transaction);

        return TransactionRespDto.builder()
                .transactionId(savedTrans.getTransactionId())
                .status("Success")
                .timestamp(savedTrans.getTimestamp())
                .build();
    }

    @Transactional(dontRollbackOn = {InsufficientFundsException.class , AccountNotFoundException.class})
    public TransactionRespDto executeTransfer
            (ExecuteTransferReqDto req){

        UUID transactionId = req.getTransactionId();

        Transaction transaction = transactionRepo.findById(transactionId)
                .orElseThrow(
                        () -> new TransactionNotFoundException("No Transaction with this Id")
                );

        if(transaction.getStatus() != TransactionStatus.INITIATED){
            throw new TransactionNotFoundException("Transaction Already Completed");
        }

        AccountDto fromAccount = null;
        AccountDto toAccount = null;

        try {
            fromAccount = accountClient.getAccount(transaction.getFromAccount());
            toAccount = accountClient.getAccount(transaction.getToAccount());
        } catch (AccountNotFoundException e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepo.save(transaction);
            throw e;
        }

        BigDecimal amount = transaction.getAmount();

        if(amount.compareTo(BigDecimal.ZERO) > 0){
            if(fromAccount.getBalance().compareTo(amount) < 0){

                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepo.save(transaction);

                throw new InsufficientFundsException("Account Has insufficient balance to complete operation");
            }
        }
        else{
            if(toAccount.getBalance().compareTo(amount) < 0){

                transaction.setStatus(TransactionStatus.FAILED);
                transactionRepo.save(transaction);

                throw new InsufficientFundsException("Account Has insufficient balance to complete operation");
            }
        }

        accountClient.transferAmount(
                fromAccount.getAccountId(),
                toAccount.getAccountId(),
                amount
        );

        transaction.setStatus(TransactionStatus.SUCCESS);
        transactionRepo.save(transaction);

        return TransactionRespDto.builder()
                .transactionId(transaction.getTransactionId())
                .status("Success")
                .timestamp(LocalDateTime.now())
                .build();
    }
}

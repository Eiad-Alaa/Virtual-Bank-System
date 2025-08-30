package com.transaction.transactionservice.controllers;

import com.transaction.transactionservice.dto.ExecuteTransferReqDto;
import com.transaction.transactionservice.dto.TransactionDto;
import com.transaction.transactionservice.dto.TransactionInitiationReqDto;
import com.transaction.transactionservice.dto.TransactionRespDto;
import com.transaction.transactionservice.services.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransactionRespDto> initiateTransaction
            (@RequestBody @Valid TransactionInitiationReqDto req){

        TransactionRespDto resp = transactionService.initiateTransaction(req);

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransactionRespDto> executeTransfer
            (@RequestBody @Valid ExecuteTransferReqDto req){

        TransactionRespDto resp = transactionService.executeTransfer(req);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions
            (@PathVariable UUID accountId){

        List<TransactionDto> transactions =
                transactionService.getAccountTransactions(accountId);

        if(transactions.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(transactions);
    }
}

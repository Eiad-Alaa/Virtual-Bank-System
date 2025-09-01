package com.transaction.transactionservice.controllers;

import com.transaction.transactionservice.dto.ExecuteTransferReqDto;
import com.transaction.transactionservice.dto.TransactionDto;
import com.transaction.transactionservice.dto.TransactionInitiationReqDto;
import com.transaction.transactionservice.dto.TransactionRespDto;
import com.transaction.transactionservice.producer.LogProducer;
import com.transaction.transactionservice.services.TransactionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private LogProducer logProducer;

    @Autowired
    private final TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransactionRespDto> initiateTransaction
            (@RequestBody @Valid TransactionInitiationReqDto req){
        logAsJson(req, "Request");
        TransactionRespDto resp = transactionService.initiateTransaction(req);
        logAsJson(resp, "Response");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransactionRespDto> executeTransfer
            (@RequestBody @Valid ExecuteTransferReqDto req){
        logAsJson(req, "Request");
        TransactionRespDto resp = transactionService.executeTransfer(req);
        logAsJson(resp, "Response");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionDto>> getAccountTransactions
            (@PathVariable UUID accountId){
        logAsJson(accountId, "Request");
        List<TransactionDto> transactions =
                transactionService.getAccountTransactions(accountId);
        logAsJson(transactions, "Response");
        if(transactions.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(transactions);
    }

    private void logAsJson(Object obj, String type) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            logProducer.sendLog(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

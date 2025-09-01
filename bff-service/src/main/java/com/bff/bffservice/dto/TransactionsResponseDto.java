package com.bff.bffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsResponseDto {

    private UUID transactionId;
    private Double amount;
    private UUID fromAccount;
    private UUID toAccountId;
    private String description;
    private String timestamp;

}

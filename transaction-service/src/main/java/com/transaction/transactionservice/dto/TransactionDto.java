package com.transaction.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private UUID transactionId;
    private UUID fromAccount;
    private UUID toAccount;
    private BigDecimal amount;
    private String description;
    private LocalDateTime timestamp;
    private String status;

}

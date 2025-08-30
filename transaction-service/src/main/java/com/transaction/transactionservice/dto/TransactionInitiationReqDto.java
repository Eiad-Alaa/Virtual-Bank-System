package com.transaction.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInitiationReqDto {

    @NotNull(message = "AccountIds can't be empty")
    private UUID fromAccountId;

    @NotNull(message = "AccountIds can't be empty")
    private UUID toAccountId;

    @NotNull(message = "amount can't be empty")
    private BigDecimal amount;

    private String description;
}

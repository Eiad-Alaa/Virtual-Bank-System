package com.account.accountservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {

    @NotNull(message = "AccountIds can't be empty")
    private UUID fromAccountId;

    @NotNull(message = "AccountIds can't be empty")
    private UUID toAccountId;

    @NotNull(message = "amount can't be empty")
    @Min(value = 0 , message = "amount can't be a negative number")
    private BigDecimal amount;
}

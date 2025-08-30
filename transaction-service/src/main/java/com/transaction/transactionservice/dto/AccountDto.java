package com.transaction.transactionservice.dto;

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
public class AccountDto {

    private UUID accountId;
    private String accountNum;
    private String accountType;
    private BigDecimal balance;
    private String status;

}

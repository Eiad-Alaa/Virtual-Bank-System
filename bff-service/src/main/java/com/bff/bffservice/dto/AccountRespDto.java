package com.bff.bffservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRespDto {

    private UUID accountId;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private List<TransactionsResponseDto> transcations;
}

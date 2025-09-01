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
public class AccountDto {

    private UUID accountId;
    private String accountNum;
    private String accountType;
    private Double balance;
    private String status;

}

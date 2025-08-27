package com.account.accountservice.dto;

import com.account.accountservice.constants.AccountStatus;
import com.account.accountservice.constants.AccountTypes;
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
    private AccountTypes accountType;
    private Long balance;
    private AccountStatus status;

}

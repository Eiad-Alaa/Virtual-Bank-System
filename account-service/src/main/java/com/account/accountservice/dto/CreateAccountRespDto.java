package com.account.accountservice.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CreateAccountRespDto {

    private UUID accountId;
    private String accountNumber;
    private String message;

}
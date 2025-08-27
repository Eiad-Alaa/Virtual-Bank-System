package com.account.accountservice.dto;

import com.account.accountservice.constants.AccountTypes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountReqDto {

    @NotNull(message = "UserId Must not be blank")
    private UUID userId;

    @NotNull(message = "Account Type must not be blank")
    private AccountTypes accountType;

    @NotNull(message = "Balance must not be blank")
    @Min(value = 0 , message = "Initial Balance must not be negative")
    private Long initialBalance;
}

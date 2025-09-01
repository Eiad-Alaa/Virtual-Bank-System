package com.bff.bffservice.mappers;

import com.bff.bffservice.dto.AccountDto;
import com.bff.bffservice.dto.AccountRespDto;
import com.bff.bffservice.dto.DashboardResp;
import com.bff.bffservice.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DashboardResponseMapper {


    public DashboardResp mapToResponse
            (UserProfileResponse user , List<AccountRespDto> accounts){

        return DashboardResp.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accounts(accounts)
                .build();
    }
}

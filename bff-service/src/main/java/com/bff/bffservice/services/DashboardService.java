package com.bff.bffservice.services;

import com.bff.bffservice.dto.AccountDto;
import com.bff.bffservice.dto.AccountRespDto;
import com.bff.bffservice.dto.DashboardResp;
import com.bff.bffservice.dto.UserProfileResponse;
import com.bff.bffservice.mappers.AccountResponseMapper;
import com.bff.bffservice.mappers.DashboardResponseMapper;
import com.bff.bffservice.mappers.TransactionResponseMapper;
import com.bff.bffservice.utils.AccountClient;
import com.bff.bffservice.utils.TransactionClient;
import com.bff.bffservice.utils.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserClient userClient;
    private final AccountClient accountClient;
    private final TransactionClient transactionClient;

    private final TransactionResponseMapper txMapper;
    private final AccountResponseMapper accMapper;
    private final DashboardResponseMapper dashboardMapper;

    public Mono<DashboardResp> getDashboard(UUID userId){

        //Get user profiles
        Mono<UserProfileResponse> userProfile = userClient.getUserProfile(userId);

        //Get user accounts
        Mono<List<AccountDto>> userAccounts = accountClient.getUserAccounts(userId);

        Mono<List<AccountRespDto>> accResp = userAccounts.flatMapMany(Flux::fromIterable)
                .flatMap(accountDto ->
                    transactionClient.getAccountTransactions(accountDto.getAccountId())
                            .map(txList -> txList.stream()
                                    //convert transactions received from endpoint to resp format
                                    .map(txMapper::mapToResp)
                                    .collect(Collectors.toList())
                            )
                            //convert account received from endpoint to resp format
                            .map(mappedTransactions ->
                                    accMapper.toAccountResp(accountDto , mappedTransactions))
                        )
                .collectList();

        //create dashboard resp
        return Mono.zip(userProfile , accResp)
                .map(tuple -> {
                    UserProfileResponse user = tuple.getT1();
                    List<AccountRespDto> accounts = tuple.getT2();
                    return dashboardMapper.mapToResponse(user , accounts);
                });
    }
}

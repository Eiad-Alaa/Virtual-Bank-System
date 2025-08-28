package com.account.accountservice.utils;

import com.account.accountservice.constants.AccountStatus;
import com.account.accountservice.entity.Account;
import com.account.accountservice.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InactiveAccountsScheduledJob {

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void inactiveAccountsSetter(){
        //LocalDateTime boundaryTime = LocalDateTime.now().minusDays(1);
        LocalDateTime boundaryTime = LocalDateTime.now().minusMinutes(2);

        List<Account> inActiveAccounts = accountRepository.findByStatusAndUpdatedAtBefore(
                AccountStatus.ACTIVE , boundaryTime
        );

        if(!inActiveAccounts.isEmpty()){
            inActiveAccounts.forEach(account -> {
                account.setStatus(AccountStatus.INACTIVE);
            });

            accountRepository.saveAll(inActiveAccounts);
        }

    }
}

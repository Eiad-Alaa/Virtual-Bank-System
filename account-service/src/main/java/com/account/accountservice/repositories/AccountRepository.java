package com.account.accountservice.repositories;

import com.account.accountservice.constants.AccountStatus;
import com.account.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findByUserId(UUID userId);

    List<Account> findByStatusAndUpdatedAtBefore(
        AccountStatus status,
        LocalDateTime boundaryTime
    );
}

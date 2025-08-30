package com.account.accountservice.entity;

import com.account.accountservice.constants.AccountStatus;
import com.account.accountservice.constants.AccountTypes;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account" , schema = "vbank")
public class Account {

    @Id
    @Column(name = "account_id" , updatable = false , nullable = false , columnDefinition = "UUID")
    private UUID accountId;

    @Column(name = "UserId" , nullable = false)
    private UUID userId;

    @Size(min = 8 , max = 12, message = "Account Number must be between 8 and 12 characters")
    @Column(name = "account_number" , nullable = false , unique = true , length = 12)
    private String accountNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type" , nullable = false)
    private AccountTypes accountTypes;

    @Min(value = 0 , message = "Balance cannot be negative")
    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    @Column(name = "created_at" , updatable = false , nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "last_transaction_at")
    private LocalDateTime lastTransactionAt;

}

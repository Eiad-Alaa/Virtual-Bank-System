package com.transaction.transactionservice.entity;

import com.transaction.transactionservice.constants.TransactionStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction" , schema = "vbank")
public class Transaction {

    @Id
    @Column(name = "transaction_id" , nullable = false)
    private UUID transactionId;

    @Column(name = "from_account" , nullable = false)
    private UUID fromAccount;

    @Column(name = "to_account" , nullable = false)
    private UUID toAccount;

    @Column(name = "amount" , nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at" , nullable = false)
    private LocalDateTime timestamp;
}

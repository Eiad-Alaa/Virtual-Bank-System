package com.account.accountservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer" , schema = "vbank")
public class Transfer {

    @Id
    @Column(name = "transaction_id")
    private UUID transactionId;

    @ManyToOne
    @JoinColumn(name = "from_account" , nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account" , nullable = false)
    private Account toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    private Timestamp createdAt;
}

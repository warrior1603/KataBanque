package com.example.katabanque.persistence.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Operation")
public class OperationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    @Column(name = "operation")
    private OperationType operationType;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "balance")
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;
}
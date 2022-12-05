package com.example.katabanque.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    private Long operationId;
    private OperationType operationType;
    private Date date;
    private double amount;
    private double balance;
}

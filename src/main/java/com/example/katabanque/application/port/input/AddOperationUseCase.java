package com.example.katabanque.application.port.input;


import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.OperationType;

public interface AddOperationUseCase {

    void addOperation(Long accountId, Money money, OperationType operationType);

}

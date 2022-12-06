package com.example.katabanque.domain.port;

import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.OperationType;
import com.example.katabanque.persistence.entity.OperationEntity;

import java.util.List;

public interface OperationPersistencePort {
    List<OperationEntity> listOperations(Long accountId);
    void addOperation(Long accountId, Money money, OperationType operationType);
}

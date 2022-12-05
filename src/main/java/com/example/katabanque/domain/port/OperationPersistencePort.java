package com.example.katabanque.domain.port;

import com.example.katabanque.persistence.entity.OperationEntity;

import java.util.List;

public interface OperationPersistencePort {
    List<OperationEntity> listOperations(Long accountId);
}

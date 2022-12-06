package com.example.katabanque.persistence.repository;

import com.example.katabanque.persistence.entity.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
    List<OperationEntity> findByAccountId(Long accountId);
}

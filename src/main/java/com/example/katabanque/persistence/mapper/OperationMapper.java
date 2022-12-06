package com.example.katabanque.persistence.mapper;

import com.example.katabanque.domain.model.Operation;
import com.example.katabanque.persistence.entity.OperationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    List<Operation> map(List<OperationEntity> operation);
    Operation map(OperationEntity operationEntity);
}

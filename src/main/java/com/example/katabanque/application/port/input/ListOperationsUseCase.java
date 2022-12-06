package com.example.katabanque.application.port.input;

import com.example.katabanque.domain.model.Operation;

import java.util.List;

public interface ListOperationsUseCase {

    List<Operation> listOperations(Long id);
}

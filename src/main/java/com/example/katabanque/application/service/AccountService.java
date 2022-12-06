package com.example.katabanque.application.service;


import com.example.katabanque.application.port.input.AddOperationUseCase;
import com.example.katabanque.application.port.input.DepositUseCase;
import com.example.katabanque.application.port.input.ListOperationsUseCase;
import com.example.katabanque.application.port.input.WithdrawUseCase;
import com.example.katabanque.domain.model.Account;
import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.Operation;
import com.example.katabanque.domain.model.OperationType;
import com.example.katabanque.domain.port.AccountPersistencePort;
import com.example.katabanque.domain.port.OperationPersistencePort;
import com.example.katabanque.persistence.entity.AccountEntity;
import com.example.katabanque.persistence.entity.OperationEntity;
import com.example.katabanque.persistence.mapper.AccountMapper;
import com.example.katabanque.persistence.mapper.OperationMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AccountService implements DepositUseCase, WithdrawUseCase, ListOperationsUseCase, AddOperationUseCase {

    private AccountPersistencePort accountPersistencePort;

    private OperationPersistencePort operationPersistencePort;

    private AccountMapper accountMapper;

    private OperationMapper operationMapper;

    @Override
    public Account deposit(Long accountId, Money money) {
        AccountEntity account = accountPersistencePort.deposit(accountId, money);
        return accountMapper.map(account);
    }

    @Override
    public Account withdraw(Long accountId, Money money) {
        AccountEntity account = accountPersistencePort.withdraw(accountId, money);
        return accountMapper.map(account);
    }

    @Override
    public List<Operation> listOperations(Long accountId) {
        List<OperationEntity> operationList = operationPersistencePort.listOperations(accountId);
        return operationMapper.map(operationList);
    }

    @Override
    public void addOperation(Long accountId, Money money, OperationType operationType) {
        operationPersistencePort.addOperation(accountId, money, operationType);
    }
}

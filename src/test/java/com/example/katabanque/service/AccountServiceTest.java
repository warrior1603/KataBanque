package com.example.katabanque.service;


import com.example.katabanque.application.service.AccountService;
import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.OperationType;
import com.example.katabanque.domain.port.AccountPersistencePort;
import com.example.katabanque.domain.port.OperationPersistencePort;
import com.example.katabanque.persistence.entity.AccountEntity;
import com.example.katabanque.persistence.entity.OperationEntity;
import com.example.katabanque.persistence.mapper.AccountMapper;
import com.example.katabanque.persistence.mapper.OperationMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static com.example.katabanque.util.TestsUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountPersistencePort accountPersistencePort;

    @Mock
    private OperationPersistencePort operationPersistencePort;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private OperationMapper operationMapper;

    private AccountEntity account;

    private Money money;

    @Before
    public void setUp() {
        account = getAccountEntity();

        money = new Money();
        money.setAmount(DEPOSIT_AMOUNT);
    }

    public AccountEntity getAccountEntity() {
        account = new AccountEntity();
        account.setId(ACCOUNT_ID);
        account.setBalance(INITIAL_AMOUNT);
        account.setOperations(List.of(getOperationEntity()));
        return account;
    }

    public OperationEntity getOperationEntity() {
        OperationEntity operation = new OperationEntity();
        operation.setOperationId(OPERATION_ID);
        operation.setAccount(account);
        operation.setDate(new Date(System.currentTimeMillis()));
        operation.setAmount(DEPOSIT_AMOUNT);
        operation.setBalance(account.getBalance() + DEPOSIT_AMOUNT);
        operation.setOperationType(OperationType.DEPOSIT);
        return operation;
    }

    @Tag(MAKE_DEPOSIT_TAG)
    @Test
    public void should_increase_account_balance_when_making_deposit() {
        when(accountPersistencePort.deposit(ACCOUNT_ID, money)).thenReturn(account);
        assertEquals(accountMapper.map(account), accountService.deposit(ACCOUNT_ID, money));
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_decrease_account_balance_when_withdraw_amount_from_account() {
        when(accountPersistencePort.withdraw(ACCOUNT_ID, money)).thenReturn(account);
        assertEquals(accountMapper.map(account), accountService.withdraw(ACCOUNT_ID, money));
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_throw_exception_when_withdrawing_null_amount_from_account() {
        Money nullAmount = new Money(0);
        when(accountPersistencePort.withdraw(ACCOUNT_ID, nullAmount)).thenReturn(account);
        assertEquals(accountMapper.map(account), accountService.withdraw(ACCOUNT_ID, nullAmount));
    }

    @Tag(PRINT_OPERATIONS_TAG)
    @Test
    public void should_return_operations_list() {
        List<OperationEntity> operations = List.of(getOperationEntity());
        when(operationPersistencePort.listOperations(account.getId())).thenReturn(operations);
        assertEquals(operationMapper.map(operations), accountService.listOperations(account.getId()));
    }
}
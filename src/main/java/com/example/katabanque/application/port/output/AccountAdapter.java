package com.example.katabanque.application.port.output;

import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.OperationType;
import com.example.katabanque.domain.port.AccountPersistencePort;
import com.example.katabanque.domain.port.OperationPersistencePort;
import com.example.katabanque.persistence.entity.AccountEntity;
import com.example.katabanque.persistence.entity.OperationEntity;
import com.example.katabanque.persistence.repository.AccountRepository;
import com.example.katabanque.persistence.repository.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class AccountAdapter implements AccountPersistencePort, OperationPersistencePort {

    private AccountRepository accountRepository;

    private OperationRepository operationRepository;

    @Override
    public AccountEntity deposit(Long accountId, Money money) {
        try {
            depositOrWithDrawAmount(money);
            AccountEntity account = getAccountById(accountId);
            account.setBalance(account.getBalance() + money.getAmount());
            addOperation(account.getId(), money, OperationType.DEPOSIT);
            log.info("{} euros successfully deposited to account {}", money.getAmount(), accountId);
            return accountRepository.saveAndFlush(account);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getStatusText());
        }
    }

    @Override
    public AccountEntity withdraw(Long accountId, Money money) {
        try {
            depositOrWithDrawAmount(money);
            AccountEntity account = getAccountById(accountId);
            if (money.getAmount() > account.getBalance()) {
                log.error("Insufficient balance to withdraw amount");
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Insufficient balance to withdraw amount");
            }
            account.setBalance(account.getBalance() - money.getAmount());
            addOperation(account.getId(), money, OperationType.WITHDRAW);
            log.info("{} euros successfully withdrawn from account {}", money.getAmount(), accountId);
            return accountRepository.saveAndFlush(account);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getStatusText());
        }
    }

    @Override
    public List<OperationEntity> listOperations(Long accountId) {
         AccountEntity account = getAccountById(accountId);
         log.info("Statement successfully printed for account {}", accountId);
         return operationRepository.findByAccountId(account.getId());
    }

    @Override
    public void addOperation(Long accountId, Money money, OperationType operationType) {
        AccountEntity account = getAccountById(accountId);
        OperationEntity operation = new OperationEntity();
        operation.setAccount(account);
        operation.setAmount(money.getAmount());
        operation.setOperationType(operationType);
        operation.setDate(new Date(System.currentTimeMillis()));
        operation.setBalance(account.getBalance());
        operationRepository.saveAndFlush(operation);
    }

    public AccountEntity getAccountById(Long accountId) {
        Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            log.error("No existing account with id {}", accountId);
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existing account with this id");
        }
        return optionalAccount.get();
    }

    public void depositOrWithDrawAmount(Money money) {
        if (!money.isPositive()) {
            log.error("Cannot deposit or withdraw a negative or null amount");
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                    "Cannot deposit or withdraw a negative or null amount");
        }
    }
}

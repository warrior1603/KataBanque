package com.example.katabanque.domain.port;

import com.example.katabanque.domain.model.Money;
import com.example.katabanque.persistence.entity.AccountEntity;

public interface AccountPersistencePort {
    AccountEntity deposit(Long accountId, Money money);
    AccountEntity withdraw(Long accountId, Money money);
}

package com.example.katabanque.application.port.input;

import com.example.katabanque.domain.model.Account;
import com.example.katabanque.domain.model.Money;

public interface DepositUseCase {

    Account deposit(Long id, Money money);
}

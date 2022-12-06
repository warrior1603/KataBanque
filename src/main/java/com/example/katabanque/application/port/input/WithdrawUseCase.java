package com.example.katabanque.application.port.input;

import com.example.katabanque.domain.model.Account;
import com.example.katabanque.domain.model.Money;

public interface WithdrawUseCase {

    Account withdraw(Long id, Money money);
}

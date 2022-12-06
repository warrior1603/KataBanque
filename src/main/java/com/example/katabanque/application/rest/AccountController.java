package com.example.katabanque.application.rest;

import com.example.katabanque.application.port.input.DepositUseCase;
import com.example.katabanque.application.port.input.ListOperationsUseCase;
import com.example.katabanque.application.port.input.WithdrawUseCase;
import com.example.katabanque.domain.model.Account;
import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.Operation;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private DepositUseCase depositUseCase;
    private WithdrawUseCase withdrawUseCase;
    private ListOperationsUseCase listOperationsUseCase;

    @PutMapping("/deposit/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account depositMoney(@PathVariable @NotNull Long accountId, @RequestBody @NotNull Money money){
        return depositUseCase.deposit(accountId, money);
    }

    @PutMapping("/withdraw/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Account withdrawMoney(@PathVariable @NotNull Long accountId, @RequestBody @NotNull Money money) {
        return withdrawUseCase.withdraw(accountId, money);
    }

    @GetMapping("/print/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Operation> printOperations(@PathVariable @NotNull Long accountId) {
        return listOperationsUseCase.listOperations(accountId);
    }

}

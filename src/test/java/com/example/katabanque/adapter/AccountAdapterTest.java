package com.example.katabanque.adapter;

import com.example.katabanque.application.port.output.AccountAdapter;
import com.example.katabanque.domain.model.Money;
import com.example.katabanque.persistence.repository.AccountRepository;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;


import static com.example.katabanque.util.TestsUtil.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class AccountAdapterTest {

    @InjectMocks
    private AccountAdapter accountAdapter;

    @Mock
    private AccountRepository accountRepository;

    @Tag(MAKE_DEPOSIT_TAG)
    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void deposit_or_withdraw_amount_throws_exception_when_amount_is_negative_or_null(){
        Money invalidAmount = new Money(-1000);
        assertThrows(HttpClientErrorException.class,
                () -> accountAdapter.depositOrWithDrawAmount(invalidAmount));
    }

    @Tag(MAKE_DEPOSIT_TAG)
    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void deposit_or_withdraw_amount_does_not_throw_exception_when_amount_is_positive(){
        Money validAmount = new Money(1000);
        assertDoesNotThrow(() -> accountAdapter.depositOrWithDrawAmount(validAmount));
    }

    @Tag(ACCOUNT_REQUESTS_TAG)
    @Test
    public void get_account_by_id_throws_exception_when_account_id_does_not_exist(){
        assertThrows(HttpClientErrorException.class,
                () -> accountAdapter.getAccountById(10000L));
    }
}

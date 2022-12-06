package com.example.katabanque.controller;

import com.example.katabanque.domain.model.Account;
import com.example.katabanque.domain.model.Money;
import com.example.katabanque.domain.model.Operation;
import com.example.katabanque.persistence.entity.OperationEntity;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.example.katabanque.util.TestsUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerAcceptanceTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private RestTemplate restTemplate;

    private String url;

    private HttpHeaders headers;

    @Before
    public void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void resetUrl() {
        url = "http://localhost:" + randomServerPort;
    }

    @Tag(MAKE_DEPOSIT_TAG)
    @Test
    public void should_deposit_money() {
        url += DEPOSIT_ACCOUNT_URI;

        HttpEntity<Object> entity = new HttpEntity<>(new Money(DEPOSIT_AMOUNT), headers);
        ResponseEntity<Account> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(entity), Account.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_withdraw_money() {
        url += WITHDRAW_ACCOUNT_URI;

        HttpEntity<Object> entity = new HttpEntity<>(new Money(WITHDRAW_AMOUNT), headers);
        ResponseEntity<Account> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
                new HttpEntity<>(entity), Account.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_not_withdraw_money_when_withdraw_amount_bigger_than_balance() {
        url += WITHDRAW_ACCOUNT_URI;

        HttpEntity<Money> moneyHttpEntity = new HttpEntity<>(new Money(BIG_WITHDRAW_AMOUNT), headers);
        assertThrows(HttpServerErrorException.class, () -> restTemplate.exchange(url, HttpMethod.PUT,
                moneyHttpEntity, Money.class));
    }

    @Tag(PRINT_OPERATIONS_TAG)
    @Test
    @SuppressWarnings("unchecked")
    public void should_print_operations() {
        url += PRINT_OPERATIONS_URI;
        OperationEntity operation = new OperationEntity();
        ResponseEntity<List<Operation>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(List.of(operation)), (Class<List<Operation>>)(Object)List.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}


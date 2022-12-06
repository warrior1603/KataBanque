package com.example.katabanque.controller;

import com.example.katabanque.domain.model.Money;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.katabanque.util.TestsUtil.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Tag(MAKE_DEPOSIT_TAG)
    @Test
    public void should_deposit_money_in_account_returns_status_200() throws Exception {
        Money money = new Money(DEPOSIT_AMOUNT);
        this.mockMvc.perform(put(DEPOSIT_ACCOUNT_URI)
                        .content(getRequestBody(money))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_not_decrease_account_balance_returns_status_400() throws Exception {
        Money money = new Money(BIG_WITHDRAW_AMOUNT);
        this.mockMvc.perform(put(WITHDRAW_ACCOUNT_URI)
                        .content(getRequestBody(money))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Tag(WITHDRAW_MONEY_TAG)
    @Test
    public void should_decrease_account_balance_returns_status_200() throws Exception {
        Money money = new Money(WITHDRAW_AMOUNT);
        this.mockMvc.perform(put(WITHDRAW_ACCOUNT_URI)
                        .content(getRequestBody(money))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Tag(PRINT_OPERATIONS_TAG)
    @Test
    public void should_print_operations_returns_status_200() throws Exception {
        this.mockMvc.perform(get(PRINT_OPERATIONS_URI)).andExpect(status().isOk());
    }

    public String getRequestBody(Money money) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(money);
    }


}


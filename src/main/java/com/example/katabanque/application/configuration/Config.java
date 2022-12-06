package com.example.katabanque.application.configuration;

import com.example.katabanque.application.port.input.DepositUseCase;
import com.example.katabanque.application.port.output.AccountAdapter;
import com.example.katabanque.application.service.AccountService;
import com.example.katabanque.domain.port.AccountPersistencePort;
import com.example.katabanque.domain.port.OperationPersistencePort;
import com.example.katabanque.persistence.mapper.AccountMapper;
import com.example.katabanque.persistence.mapper.OperationMapper;
import com.example.katabanque.persistence.repository.AccountRepository;
import com.example.katabanque.persistence.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Configuration
public class Config {

    private AccountRepository accountRepository;

    private OperationRepository operationRepository;

    private AccountMapper accountMapper;

    private OperationMapper operationMapper;

    @Bean
    public AccountPersistencePort accountPersistence() {
        return new AccountAdapter(accountRepository, operationRepository);
    }

    @Bean
    public OperationPersistencePort operationPersistence() {
        return new AccountAdapter(accountRepository, operationRepository);
    }

    @Bean
    public DepositUseCase depositUseCase(){
        return new AccountService(accountPersistence(), operationPersistence(), accountMapper, operationMapper);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

package com.example.katabanque.persistence.mapper;

import com.example.katabanque.domain.model.Account;
import com.example.katabanque.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account map(AccountEntity account);

}

package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.entity.Account;


public interface IAccountDAO {

    Optional<Account> read(Long id);

    List<Account> list(Long id);

    Account create(Account newRole);
    
    void update(Account newAccount, Double amount, Long id);
    
    void delete(Long id);

    AccountDTO toDTO(Account account);

    Account toEntity(AccountPostDTO newAccount);

    Account toEntity(AccountDTO newAccount);

}

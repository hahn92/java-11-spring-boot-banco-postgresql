package com.hahn.banco.service;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;

public interface IAccountService {

    Optional<AccountDTO> getById(Long id);

    AccountDTO save(AccountPostDTO newAccount, Long id_user, Long id_bank);

    Double getBalanceByClientId(Long id);

    List<AccountDTO> getAccountByClientId(Long id);
    
}

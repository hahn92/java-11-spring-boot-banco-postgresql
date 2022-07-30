package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;

public interface IAccountService {

    Optional<AccountDTO> getById(Long id);

    AccountDTO save(AccountPostDTO newAccount, Long id_user, Long id_bank);

    Double getBalanceById(Long id);
    
}

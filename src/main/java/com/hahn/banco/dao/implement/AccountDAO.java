package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IAccountDAO;
import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.entity.Account;
import com.hahn.banco.repository.AccountRepository;

@Component
public class AccountDAO implements IAccountDAO {

    @Autowired
    private AccountRepository accountRepository;
	

    @Override
    public Account create(Account newAccount) {
        return accountRepository.save(newAccount);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);   
    }

    @Override
    public List<Account> list(Long id_client) {
        return accountRepository.findByClientId(id_client);
    }

    @Override
    public Optional<Account> read(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void update(Account newAccount, Double amount, Long id) {
        this.read(id).map(
                account -> {
                    account.setBeginBalance(account.getBalance());
                    account.setBalance(amount);
                    accountRepository.save(account);
                    return true;
                }
        );
    }

    @Override
    public AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getId(), null, null, account.getAccountNumber(), account.getBalance(), account.getBeginBalance(), account.getAccountType(), account.getState());
    }

    @Override
    public Account toEntity (AccountPostDTO accountDTO) {
        return new Account(null, null, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    }

    @Override
    public Account toEntity (AccountDTO accountDTO) {
        return new Account(accountDTO.getId(), null, null, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType());
    }

}

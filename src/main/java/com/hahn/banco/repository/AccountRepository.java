package com.hahn.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Account;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

    Optional<Account> findById(Long id);

    List<Account> findByClientId(Long id);

    void deleteById(Long id);

    List<Account> findByAccountNumber(String accountNumber);
    
}

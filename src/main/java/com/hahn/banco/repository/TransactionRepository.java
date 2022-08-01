package com.hahn.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Transaction;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

    Optional<Transaction> findById(Long id);

    void deleteById(Long id);

    List<Transaction> findByName(String name);
    
}

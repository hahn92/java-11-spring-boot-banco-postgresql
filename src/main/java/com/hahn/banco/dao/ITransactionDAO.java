package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;
import com.hahn.banco.entity.Transaction;


public interface ITransactionDAO {

    Optional<Transaction> read(Long id);

    List<Transaction> list(String name);

    Transaction create(Transaction newRole);
    
    void update(Transaction newRole, Long id);
    
    void delete(Long id);

    TransactionDTO toDTO(Transaction transaction);

    Transaction toEntity(TransactionPostDTO newTransaction);

    Transaction toEntity(TransactionDTO newTransaction);

}

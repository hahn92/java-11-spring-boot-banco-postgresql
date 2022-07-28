package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;

public interface ITransactionService {

    Optional<TransactionDTO> getById(Long id);

    TransactionDTO save(TransactionPostDTO newTransaction);
    
}

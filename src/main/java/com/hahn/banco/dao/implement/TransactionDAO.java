package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.ITransactionDAO;
import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;
import com.hahn.banco.entity.Transaction;
import com.hahn.banco.repository.TransactionRepository;

@Component
public class TransactionDAO implements ITransactionDAO {

    @Autowired
    private TransactionRepository transactionRepository;
	

    @Override
    public Transaction create(Transaction newTransaction) {
        return transactionRepository.save(newTransaction);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);   
    }

    @Override
    public List<Transaction> list(String name) {
        //return transactionRepository.findByName(name);
        return null;
    }

    @Override
    public Optional<Transaction> read(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public void update(Transaction newTransaction, Long id) {
        this.read(id).map(
                transaction -> {
                    transactionRepository.save(transaction);
                    return true;
                }
        );
    }

    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getId(), transaction.getState());
    }

    @Override
    public Transaction toEntity (TransactionPostDTO TransactionDTO) {
        return new Transaction();   
    }

    @Override
    public Transaction toEntity (TransactionDTO transactionDTO) {
        return new Transaction(transactionDTO.getId());   
    }
    
}

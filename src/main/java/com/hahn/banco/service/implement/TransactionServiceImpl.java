package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;
import com.hahn.banco.entity.Transaction;
import com.hahn.banco.repository.TransactionRepository;
import com.hahn.banco.service.ITransactionService;


@Service
public class TransactionServiceImpl implements ITransactionService {
	
    private static final Log LOGGER = LogFactory.getLog(TransactionServiceImpl.class);
	
    @Autowired
    private TransactionRepository transactionRepository;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override   
    public Optional<TransactionDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Transaction transaction = transactionRepository.findById(id).get();
        if(transaction.getId() != null) {
            LOGGER.debug("+++ TransactionServiceImpl:getById: "+transaction.toString());
            return Optional.of(this.toDTO(transaction));
        }
        LOGGER.debug("--- TransactionServiceImpl:getById: No existe la transacion con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO save(TransactionPostDTO newTransaction) {
        // TODO Auto-generated method stub
        Transaction transaction = this.toEntity(newTransaction); 
        LOGGER.debug("+++ TransactionServiceImpl:save: "+transaction.toString());
        return this.toDTO(transactionRepository.save(transaction));
    }


    public TransactionDTO toDTO(Transaction transaction) {
        LOGGER.debug("+++ TransactionServiceImpl:toDTO: "+transaction.toString());
        return new TransactionDTO(transaction.getId(), transaction.getState());
    }

    public Transaction toEntity (TransactionPostDTO transactionDTO) {
        LOGGER.debug("+++ TransactionServiceImpl:toEntity: "+transactionDTO.toString());
        return new Transaction();   
    }

    public Transaction toEntity (TransactionDTO transactionDTO) {
        LOGGER.debug("+++ TransactionServiceImpl:toEntity: "+transactionDTO.toString());
        return new Transaction(transactionDTO.getId());   
    }

}

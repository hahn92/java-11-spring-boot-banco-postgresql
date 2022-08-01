package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.ITransactionDAO;
import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;
import com.hahn.banco.entity.Transaction;
import com.hahn.banco.service.ITransactionService;


@Service
public class TransactionServiceImpl implements ITransactionService {
	
    private static final Log LOGGER = LogFactory.getLog(TransactionServiceImpl.class);
	
    @Autowired
    private ITransactionDAO iTransactionDAO;
	

	public TransactionServiceImpl(ITransactionDAO iTransactionDAO) {
        this.iTransactionDAO = iTransactionDAO;
    }

    
    @Override   
    public Optional<TransactionDTO> getById(Long id) {
        Transaction transaction = iTransactionDAO.read(id).get();
        if(transaction.getId() != null) {
            LOGGER.debug("+++ TransactionServiceImpl:getById: "+transaction.toString());
            return Optional.of(iTransactionDAO.toDTO(transaction));
        }
        LOGGER.debug("--- TransactionServiceImpl:getById: No existe la transacion con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionDTO save(TransactionPostDTO newTransaction) {
        Transaction transaction = iTransactionDAO.toEntity(newTransaction); 
        LOGGER.debug("+++ TransactionServiceImpl:save: "+transaction.toString());
        return iTransactionDAO.toDTO(iTransactionDAO.create(transaction));
    }

}

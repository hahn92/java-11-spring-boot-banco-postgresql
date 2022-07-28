package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;
import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.dto.transaction.TransactionPostDTO;
import com.hahn.banco.entity.Account;
import com.hahn.banco.entity.Operation;
import com.hahn.banco.entity.constant.OperationType;
import com.hahn.banco.repository.OperationRepository;
import com.hahn.banco.service.IOperationService;


@Service
public class OperationServiceImpl implements IOperationService{
	
    private static final Log LOGGER = LogFactory.getLog(OperationServiceImpl.class);
	
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;
    
    public OperationServiceImpl(OperationRepository operationRepository, AccountServiceImpl accountServiceImpl, TransactionServiceImpl transactionServiceImpl) {
        this.operationRepository = operationRepository;
        this.accountServiceImpl = accountServiceImpl;
        this.transactionServiceImpl = transactionServiceImpl;
    }

    @Override   
    public Optional<OperationDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Operation operation = operationRepository.findById(id).get();
        if(operation.getId() != null) {
            LOGGER.debug("+++ OperationServiceImpl:getById: "+operation.toString());
            return Optional.of(this.toDTO(operation));
        }
        LOGGER.debug("--- OperationServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean operation(OperationPostDTO newOperation, Long id_account1, Long id_account2) {
        // TODO Auto-generated method stub
        TransactionDTO transaction = transactionServiceImpl.save(new TransactionPostDTO());
        LOGGER.debug("+++ OperationServiceImpl:operation: "+transaction.toString());
        AccountDTO account1 = accountServiceImpl.getById(id_account1).get(); 
        LOGGER.debug("+++ OperationServiceImpl:operation: "+id_account1.toString());
        AccountDTO account2 = accountServiceImpl.getById(id_account2).get(); 
        LOGGER.debug("+++ OperationServiceImpl:operation: "+id_account1.toString());

        if(newOperation.getOperationType() == OperationType.CREDITO){
            this.Credito(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }else if (newOperation.getOperationType() == OperationType.DEBITO){
            this.Debito(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }

        return true;
    }

    public OperationDTO toDTO(Operation operation) {
        LOGGER.debug("+++ RoleServiceImpl:toDTO: "+operation.toString());
        return new OperationDTO(operation.getId(), accountServiceImpl.toDTO(operation.getAccount(), operation.getAccount().getClient().getId(), operation.getAccount().getBranchOffice().getId()), transactionServiceImpl.toDTO(operation.getTransaction()), operation.getOperationType(), operation.getBalance(), operation.getAmount(), operation.getDescription(), operation.getState());
    }

    // public Operation toEntity (AccountPostDTO accountDTO, Long id_client, Long id_branchOffice) {
    //     LOGGER.debug("+++ OperationServiceImpl:toEntity: "+accountDTO.toString());
    //     BranchOfficeDTO branchOfficeDTOP = branchOfficeServiceImpl.getById(id_branchOffice).get();
    //     BranchOffice branchOffice = branchOfficeServiceImpl.toEntity(branchOfficeDTOP, branchOfficeDTOP.getEmployee().getId());
    //     LOGGER.debug("+++ OperationServiceImpl:toEntity: "+branchOffice.toString());
    //     ClientDTO clientDTO = clientServiceImpl.getById(id_client).get();
    //     Client client = clientServiceImpl.toEntity(clientDTO, clientDTO.getAddress().getId());
    //     LOGGER.debug("+++ OperationServiceImpl:toEntity: "+client.toString());
    //     return new Operation(branchOffice, client, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    // }

    private boolean Credito(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        
        return true;
    }

    private boolean Debito(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        
        return true;
    }


}

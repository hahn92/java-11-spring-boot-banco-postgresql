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
import com.hahn.banco.entity.Client;
import com.hahn.banco.entity.Operation;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.entity.constant.OperationType;
import com.hahn.banco.repository.OperationRepository;
import com.hahn.banco.service.IOperationService;


@Service
public class OperationServiceImpl implements IOperationService{
	
    private static final Log LOGGER = LogFactory.getLog(OperationServiceImpl.class);

    private static double TRM = 4400.00;
	
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;
    @Autowired 
    private ClientServiceImpl clientServiceImpl;
    
    public OperationServiceImpl(OperationRepository operationRepository, AccountServiceImpl accountServiceImpl, TransactionServiceImpl transactionServiceImpl, ClientServiceImpl clientServiceImpl) {
        this.operationRepository = operationRepository;
        this.accountServiceImpl = accountServiceImpl;
        this.transactionServiceImpl = transactionServiceImpl;
        this.clientServiceImpl = clientServiceImpl;
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
            LOGGER.debug("+++ OperationServiceImpl:operation: "+OperationType.CREDITO);
            return this.Credito(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }else if (newOperation.getOperationType() == OperationType.DEBITO){
            LOGGER.debug("+++ OperationServiceImpl:operation: "+OperationType.DEBITO);
            return this.Debito(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }else if (newOperation.getOperationType() == OperationType.TRANSFERENCIA){
            LOGGER.debug("+++ OperationServiceImpl:operation: "+OperationType.TRANSFERENCIA);
            return this.Transferencia(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }else if (newOperation.getOperationType() == OperationType.USD){
            LOGGER.debug("+++ OperationServiceImpl:operation: "+OperationType.USD);
            return  this.Divisas(account1, newOperation.getAmount(), newOperation.getDescription(), transaction.getId());
        }else {
            LOGGER.debug("+++ OperationServiceImpl:operation: N/A: " +newOperation.getOperationType());
            return false;
        }
    }

    private boolean Credito(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method 
        LOGGER.debug("+++ OperationServiceImpl:Credito: "+account1.toString()+" "+account2.toString());
        Operation operation1 = new Operation();
        Operation operation2 = new Operation();
        operation1.setAccount(accountServiceImpl.toEntity(account1, account1.getClient().getId(), account1.getBranchOffice().getId()));
        operation2.setAccount(accountServiceImpl.toEntity(account2, account1.getClient().getId(), account1.getBranchOffice().getId()));
        Client client2 = clientServiceImpl.toEntity(account2.getClient(), account2.getClient().getAddress().getId());
        // Si el cliente es menor de 18 años no puede dejar mejor de $10.000 en la cuenta
        if (account2.getAccountType() == AccountType.AHORROS){
            LOGGER.debug("+++ OperationServiceImpl:operation: AHORROS");
            if ((account2.getBalance() > amount && client2.idOfAge()) || ((account2.getBalance() - 10000) > amount)) {
                return this.TranfrerenciaEntreCuentas(operation1, account1, operation2, account2, amount, description, id_transaction);
            }else {
                return false;
            }
        }else {
            if (account2.getBalance() > amount) {
                LOGGER.debug("+++ OperationServiceImpl:operation: CORRIENTE");
                return this.TranfrerenciaEntreCuentas(operation1, account1, operation2, account2, amount, description, id_transaction);
            }else {
                return false;
            }
        }
    }

    private boolean Debito(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        LOGGER.debug("+++ OperationServiceImpl:Debito: "+account1.toString()+" "+account2.toString());
        Operation operation1 = new Operation();
        Operation operation2 = new Operation();
        operation1.setAccount(accountServiceImpl.toEntity(account1, account1.getClient().getId(), account1.getBranchOffice().getId()));
        operation2.setAccount(accountServiceImpl.toEntity(account2, account1.getClient().getId(), account1.getBranchOffice().getId()));
        if (account1.getBalance() > amount) {
            return this.TranfrerenciaEntreCuentas(operation2, account2, operation1, account1, amount, description, id_transaction);
        }else {
            return false;
        }
    }
    
    private boolean Transferencia(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        
        return true;
    }
    private boolean Divisas(AccountDTO account1, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        Double newAmmoun = TRM*amount;
        
        return true;
    }

    // TODO: Implementar metodo para tranfrerencia entre cuentas
    // Operacion: Credito en cuenta 1 y Debito en cuenta 2
    private boolean TranfrerenciaEntreCuentas(Operation operation1, AccountDTO account1, Operation operation2, AccountDTO account2, Double amount, String description, Long id_transaction) {
        // TODO Auto-generated method stub
        LOGGER.debug("+++ OperationServiceImpl:TranfrerenciaEntreCuentas: "+operation1.toString()+" "+operation2.toString());
        LOGGER.debug("+++ OperationServiceImpl:TranfrerenciaEntreCuentas: "+account1.toString()+" "+account2.toString());
        
        operation1.setAmount(amount);
        operation1.setDescription(description);
        operation1.setTransaction(transactionServiceImpl.toEntity(transactionServiceImpl.getById(id_transaction).get()));
        operation1.setOperationType(OperationType.CREDITO);
        operation1.setBalance(account1.getBalance() + amount);
        accountServiceImpl.update(operation1.getBalance(), operation1.getAccount().getId());


        operation2.setAmount(amount);
        operation2.setDescription(description);
        operation2.setTransaction(transactionServiceImpl.toEntity(transactionServiceImpl.getById(id_transaction).get()));
        operation2.setOperationType(OperationType.DEBITO);
        operation2.setBalance(account2.getBalance() - amount);
        operationRepository.save(operation2);
        accountServiceImpl.update(operation2.getBalance(), operation2.getAccount().getId());

        return true;
    }

    
    public OperationDTO toDTO(Operation operation) {
        LOGGER.debug("+++ RoleServiceImpl:toDTO: "+operation.toString());
        return new OperationDTO(operation.getId(), accountServiceImpl.toDTO(operation.getAccount(), operation.getAccount().getClient().getId(), operation.getAccount().getBranchOffice().getId()), transactionServiceImpl.toDTO(operation.getTransaction()), operation.getOperationType(), operation.getBalance(), operation.getAmount(), operation.getDescription(), operation.getState());
    }
    

}
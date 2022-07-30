package com.hahn.banco.service.implement;

import java.util.ArrayList;
import java.util.List;
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
import com.hahn.banco.entity.Client;
import com.hahn.banco.entity.Operation;
import com.hahn.banco.entity.Transaction;
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
    public List<OperationDTO> getOperationByAccountId(Long id) {
        // TODO Auto-generated method stub
        List<Operation> operations = operationRepository.findTop5ByAccountId(id);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation op : operations) {
            operationDTOs.add(this.toDTO(op));
        }
        LOGGER.debug("+++ getOperationByAccountId:getOperationByAccountId: "+ id);
        return operationDTOs;
    }

    @Override
    public OperationDTO save(OperationDTO newOperation) {
        // TODO Auto-generated method stub
        Operation operation = this.toEntity(newOperation); 
        LOGGER.debug("+++ OperationServiceImpl:save: "+operation.toString());
        return this.toDTO(operationRepository.save(operation));
    }    

    public Operation save(Operation newOperation) {
        // TODO Auto-generated method stub
        LOGGER.debug("+++ OperationServiceImpl:save: "+newOperation.toString());
        return operationRepository.save(newOperation);
    }   

    /* 
    TODO: Implementar metodo para tranfrerencia entre cuentas
    Verifica el tipo de cuenta y la edad del cliente para realizar la transaccion aplicando las reglas del negocio
    * TRANSFERENCIA:
    * CONSIGNACIÓN: Tranferencia de fondos a una cuenta personal
    * DIVISAS: Tranfiente fondos en dolares a una cuenta personal y tranforma el monto a pesos segun el tipo de cambio del dia
    * RETIRO: Retiro de fondos a una cuenta personal
        * CUENTA DE AHORRO: Si es menor de 18 años debe mantener un saldo minimo de $10.000.00
        * CUENTA CORRIENTE: Pueden tener sobregiros hasta de $100.000.00
    Autor: Holman Hernández
    */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Boolean operation(OperationPostDTO newOperation, Long id_account1, Long id_account2) {
        TransactionDTO transaction = transactionServiceImpl.save(new TransactionPostDTO());
        LOGGER.debug("+++ OperationServiceImpl:operation: "+transaction.toString());
        AccountDTO account1 = accountServiceImpl.getById(id_account1).get(); 
        LOGGER.debug("+++ OperationServiceImpl:operation: "+id_account1.toString());

        LOGGER.debug("+++ OperationServiceImpl:operation: " +newOperation.getOperationType());
        if (newOperation.getOperationType() == OperationType.TRANSFERENCIA){
            AccountDTO account2 = accountServiceImpl.getById(id_account2).get(); 
            LOGGER.debug("+++ OperationServiceImpl:operation: "+id_account1.toString());
            return this.Transferencia(account1, account2, newOperation.getAmount(), newOperation.getDescription(), transaction.getId(), newOperation.getOperationType());
        }else if (newOperation.getOperationType() == OperationType.RETIRO){
            return this.Retiro(account1, newOperation.getAmount(), newOperation.getDescription(), transaction.getId(), account1.getAccountType(), newOperation.getOperationType());
        }else if (newOperation.getOperationType() == OperationType.CONSIGNACIÓN){
            return this.Consignacion(account1, newOperation.getAmount(), newOperation.getDescription(), transaction.getId(), newOperation.getOperationType());
        }else if (newOperation.getOperationType() == OperationType.DIVISAS){
            return  this.Divisas(account1, newOperation.getAmount(), newOperation.getDescription(), transaction.getId(), newOperation.getOperationType());
        }else {
            return false;
        }
    }

    /* 
    TODO: Metodo para tranferir entre cuentas personales
    retira de la cuenta 2 el monto de la operacion y agrega a la cuenta 1
    Autor: Holman Hernández
    */
    private boolean Transferencia(AccountDTO account1, AccountDTO account2, Double amount, String description, Long id_transaction, OperationType operationType) {
        LOGGER.debug("+++ OperationServiceImpl:Transferencia: "+account1.toString()+" "+account2.toString());
        Operation operation1 = new Operation();
        Operation operation2 = new Operation();
        operation1.setAccount(accountServiceImpl.toEntity(account1, account1.getClient().getId(), account1.getBranchOffice().getId()));
        operation2.setAccount(accountServiceImpl.toEntity(account2, account1.getClient().getId(), account1.getBranchOffice().getId()));
        return this.TranfrerenciaEntreCuentas(operation1, account1, operation2, account2, amount, description, id_transaction, operationType);
    }
    
    /* 
    TODO: Metodo consignar a una cuenta personal
    Agrega el monto de la operacion a la cuenta 
    Autor: Holman Hernández
    */
    private boolean Consignacion(AccountDTO account1, Double amount, String description, Long id_transaction, OperationType operationType) {
        LOGGER.debug("+++ OperationServiceImpl:Consignacion: "+account1.toString());
        Operation operation1 = new Operation();
        operation1.setAccount(accountServiceImpl.toEntity(account1, account1.getClient().getId(), account1.getBranchOffice().getId()));
        return OperacionEnCuenta(operation1, account1, amount, description, id_transaction, operationType);
    }

    /* 
    TODO: Metodo retiro de una cuenta personal
    Retira el monto de la operacion en la cuenta 
    Autor: Holman Hernández
    */
    private boolean Retiro(AccountDTO account, Double amount, String description, Long id_transaction, AccountType accountType, OperationType operationType) {
        Operation operation = new Operation();
        operation.setAccount(accountServiceImpl.toEntity(account, account.getClient().getId(), account.getBranchOffice().getId()));
        LOGGER.debug("+++ OperationServiceImpl:Retiro: "+account.toString());

        if (accountType == AccountType.AHORROS){
            LOGGER.debug("+++ OperationServiceImpl:operation: AHORROS");
            Client client = clientServiceImpl.toEntity(account.getClient(), account.getClient().getAddress().getId());
            if ((account.getBalance() > amount && client.idOfAge()) || ((account.getBalance() - 10000) > amount)) {
                return this.OperacionEnCuenta(operation, account, (amount * -1), description, id_transaction, operationType);
            }else{
                return false;
            }
        } if ((account.getBalance() + 100000) > amount) {
            LOGGER.debug("+++ OperationServiceImpl:operation: CORRIENTE");
            return this.OperacionEnCuenta(operation, account, (amount * -1), description, id_transaction, operationType);
        }else {
            return false;
        }
    }

    /* 
    TODO: Metodo para convertir divisas a pesos
    Convierte la divisa y agrega el monto de la operacion a la cuenta 
    Autor: Holman Hernández
    */
    private boolean Divisas(AccountDTO account1, Double amount, String description, Long id_transaction, OperationType operationType) {
        Double newAmmoun = TRM*amount;
        LOGGER.debug("+++ OperationServiceImpl:Divisas: "+account1.toString());
        Operation operation1 = new Operation();
        operation1.setAccount(accountServiceImpl.toEntity(account1, account1.getClient().getId(), account1.getBranchOffice().getId()));
        return OperacionEnCuenta(operation1, account1, newAmmoun, description, id_transaction, operationType);
    }

    /* 
    TODO: Implementar metodo para tranfrerencia entre cuentas
    retira de la cuenta 2 el monto de la operacion y agrega a la cuenta 1
    Autor: Holman Hernández
    */
    private boolean TranfrerenciaEntreCuentas(Operation operation1, AccountDTO account1, Operation operation2, AccountDTO account2, Double amount, String description, Long id_transaction, OperationType operationType) {
        LOGGER.debug("+++ OperationServiceImpl:TranfrerenciaEntreCuentas: "+operation1.toString()+" "+operation2.toString());
        LOGGER.debug("+++ OperationServiceImpl:TranfrerenciaEntreCuentas: "+account1.toString()+" "+account2.toString());
        
        operation1.setAmount(amount);
        operation1.setDescription(description);
        operation1.setTransaction(transactionServiceImpl.toEntity(transactionServiceImpl.getById(id_transaction).get()));
        operation1.setOperationType(operationType);
        Double newBalance1 = account1.getBalance() + amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance1: "+newBalance1);
        operation1.setBalance(newBalance1);
        this.save(operation1);
        accountServiceImpl.update(operation1.getBalance(), operation1.getAccount().getId());


        operation2.setAmount(amount);
        operation2.setDescription(description);
        operation2.setTransaction(transactionServiceImpl.toEntity(transactionServiceImpl.getById(id_transaction).get()));
        operation2.setOperationType(operationType);
        Double newBalance2 = account2.getBalance() - amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance2: "+newBalance2);
        operation2.setBalance(newBalance2);
        this.save(operation2);
        accountServiceImpl.update(operation2.getBalance(), operation2.getAccount().getId());

        return true;
    }

    /* 
    TODO: Implementar metodo para operacion en una cuenta
    Modifica el monto de la operacion en la cuenta
    Autor: Holman Hernández
    */
    private boolean OperacionEnCuenta(Operation operation1, AccountDTO account1, Double amount, String description, Long id_transaction, OperationType operationType) {
        LOGGER.debug("+++ OperationServiceImpl:OperacionEnCuenta: "+operation1.toString());
        LOGGER.debug("+++ OperationServiceImpl:OperacionEnCuenta: "+account1.toString());
        
        operation1.setAmount(amount);
        operation1.setDescription(description);
        operation1.setTransaction(transactionServiceImpl.toEntity(transactionServiceImpl.getById(id_transaction).get()));
        operation1.setOperationType(operationType);
        Double newBalance1 = account1.getBalance() + amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance1: "+newBalance1);
        operation1.setBalance(newBalance1);
        this.save(operation1);
        accountServiceImpl.update(operation1.getBalance(), operation1.getAccount().getId());

        return true;
    }

    
    public OperationDTO toDTO(Operation operation) {
        LOGGER.debug("+++ OperationServiceImpl:toDTO: "+operation.toString());
        return new OperationDTO(operation.getId(), accountServiceImpl.toDTO(operation.getAccount(), operation.getAccount().getClient().getId(), operation.getAccount().getBranchOffice().getId()), transactionServiceImpl.toDTO(operation.getTransaction()), operation.getOperationType(), operation.getBalance(), operation.getAmount(), operation.getDescription(), operation.getState());
    }

    public Operation toEntity (OperationDTO operationDTO) {
        LOGGER.debug("+++ OperationServiceImpl:toEntity: "+operationDTO.toString());
        Account account = accountServiceImpl.toEntity(operationDTO.getAccount(), operationDTO.getAccount().getClient().getId(), operationDTO.getAccount().getBranchOffice().getId());
        Transaction transaction = transactionServiceImpl.toEntity(operationDTO.getTransaction());
        return new Operation(account, transaction, operationDTO.getOperationType(), operationDTO.getBalance(), operationDTO.getAmount(), operationDTO.getDescription());   
    }

}

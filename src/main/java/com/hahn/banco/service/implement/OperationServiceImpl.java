package com.hahn.banco.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IAccountDAO;
import com.hahn.banco.dao.IBranchOfficeDAO;
import com.hahn.banco.dao.IClientDAO;
import com.hahn.banco.dao.IOperationDAO;
import com.hahn.banco.dao.ITransactionDAO;
import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;
import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.entity.Client;
import com.hahn.banco.entity.Operation;
import com.hahn.banco.entity.Transaction;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.entity.constant.OperationType;
import com.hahn.banco.service.IOperationService;


@Service
public class OperationServiceImpl implements IOperationService{
	
    private static final Log LOGGER = LogFactory.getLog(OperationServiceImpl.class);

    private static double TRM = 4400.00;
	
    @Autowired
    private IOperationDAO iOperationDAO;
    @Autowired
    private IAccountDAO iAccountDAO;
    @Autowired
    private ITransactionDAO iTransactionDAO;
    @Autowired 
    private IClientDAO iClientDAO;
    @Autowired
    private IBranchOfficeDAO iBranchOfficeDAO;
    

    public OperationServiceImpl(IOperationDAO iOperationDAO, IAccountDAO iAccountDAO, ITransactionDAO iTransactionDAO, IClientDAO iClientDAO, IBranchOfficeDAO iBranchOfficeDAO) {
        this.iOperationDAO = iOperationDAO;
        this.iAccountDAO = iAccountDAO;
        this.iTransactionDAO = iTransactionDAO;
        this.iClientDAO = iClientDAO;
        this.iBranchOfficeDAO = iBranchOfficeDAO;
    }

    
    @Override   
    public Optional<OperationDTO> getById(Long id) {
        Operation operation = iOperationDAO.read(id).get();
        if(operation.getId() != null) {
            LOGGER.debug("+++ OperationServiceImpl:getById: "+operation.toString());
            OperationDTO operationDTO = iOperationDAO.toDTO(operation);
            iAccountDAO.read(operation.getAccount().getId()).ifPresent(account -> operationDTO.setAccount(iAccountDAO.toDTO(account)));
            iTransactionDAO.read(operation.getTransaction().getId()).ifPresent(transaction -> operationDTO.setTransaction(iTransactionDAO.toDTO(transaction)));
            return Optional.of(operationDTO);
        }
        LOGGER.debug("--- OperationServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    public List<OperationDTO> getOperationByAccountId(Long id) {
        List<Operation> operations = iOperationDAO.readTopFive(id);
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Operation operation : operations) {
            operationDTOs.add(iOperationDAO.toDTO(operation));
        }
        LOGGER.debug("+++ getOperationByAccountId:getOperationByAccountId: "+ id);
        return operationDTOs;
    }

    @Override
    public OperationDTO save(OperationDTO newOperation) {
        Operation operation = iOperationDAO.toEntity(newOperation); 
        LOGGER.debug("+++ OperationServiceImpl:save: "+operation.toString());
        return iOperationDAO.toDTO(iOperationDAO.create(operation));
    }    

    public Operation save(Operation newOperation) {
        LOGGER.debug("+++ OperationServiceImpl:save: "+newOperation.toString());
        return iOperationDAO.create(newOperation);
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
        TransactionDTO transaction = iTransactionDAO.toDTO(iTransactionDAO.create(new Transaction()));
        LOGGER.debug("+++ OperationServiceImpl:operation: "+transaction.toString());
        AccountDTO account1 = iAccountDAO.toDTO(iAccountDAO.read(id_account1).get()); 
        LOGGER.debug("+++ OperationServiceImpl:operation: "+id_account1.toString());

        LOGGER.debug("+++ OperationServiceImpl:operation: " +newOperation.getOperationType());
        if (newOperation.getOperationType() == OperationType.TRANSFERENCIA){
            AccountDTO account2 = iAccountDAO.toDTO(iAccountDAO.read(id_account2).get()); 
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
        operation1.setAccount(iAccountDAO.toEntity(account1));
        operation2.setAccount(iAccountDAO.toEntity(account2));
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
        operation1.setAccount(iAccountDAO.toEntity(account1));
        return OperacionEnCuenta(operation1, account1, amount, description, id_transaction, operationType);
    }

    /* 
    TODO: Metodo retiro de una cuenta personal
    Retira el monto de la operacion en la cuenta 
    Autor: Holman Hernández
    */
    private boolean Retiro(AccountDTO account, Double amount, String description, Long id_transaction, AccountType accountType, OperationType operationType) {
        Operation operation = new Operation(iAccountDAO.toEntity(account), iTransactionDAO.read(id_transaction).get(), operationType, account.getBalance(), amount, description);
        operation.setAccount(iAccountDAO.toEntity(account));
        LOGGER.debug("+++ OperationServiceImpl:Retiro: "+account.toString());

        if (accountType == AccountType.AHORROS){
            LOGGER.debug("+++ OperationServiceImpl:operation: AHORROS");
            Client client = iClientDAO.toEntity(account.getClient());
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
        operation1.setAccount(iAccountDAO.toEntity(account1));
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
        operation1.setTransaction(iTransactionDAO.read(id_transaction).get());
        operation1.setOperationType(operationType);
        Double newBalance1 = account1.getBalance() + amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance1: "+newBalance1);
        operation1.setBalance(newBalance1);
        this.save(operation1);
        iAccountDAO.update(operation1.getAccount(), newBalance1, operation1.getAccount().getId());


        operation2.setAmount(amount);
        operation2.setDescription(description);
        operation2.setTransaction(iTransactionDAO.read(id_transaction).get());
        operation2.setOperationType(operationType);
        Double newBalance2 = account2.getBalance() - amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance2: "+newBalance2);
        operation2.setBalance(newBalance2);
        this.save(operation2);
        iAccountDAO.update(operation2.getAccount(), newBalance2, operation2.getAccount().getId());

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
        operation1.setTransaction(iTransactionDAO.read(id_transaction).get());
        operation1.setOperationType(operationType);
        Double newBalance1 = account1.getBalance() + amount;
        LOGGER.debug("+++ OperationServiceImpl:newBalance1: "+newBalance1);
        operation1.setBalance(newBalance1);
        this.save(operation1);
        iAccountDAO.update(operation1.getAccount(), newBalance1, operation1.getAccount().getId());

        return true;
    }

}

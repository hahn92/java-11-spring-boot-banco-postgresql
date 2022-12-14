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
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.entity.Account;
import com.hahn.banco.entity.BranchOffice;
import com.hahn.banco.entity.Client;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.repository.AccountRepository;
import com.hahn.banco.service.IAccountService;


@Service
public class AccountServiceImpl implements IAccountService{
	
    private static final Log LOGGER = LogFactory.getLog(AccountServiceImpl.class);
	
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientServiceImpl clientServiceImpl;
    @Autowired
    private BranchOfficeServiceImpl branchOfficeServiceImpl;
	
    public AccountServiceImpl(AccountRepository accountRepository, ClientServiceImpl clientServiceImpl, BranchOfficeServiceImpl branchOfficeServiceImpl) {
        this.accountRepository = accountRepository;
        this.clientServiceImpl = clientServiceImpl;
        this.branchOfficeServiceImpl = branchOfficeServiceImpl;
    }

    @Override   
    public Optional<AccountDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Account account = accountRepository.findById(id).get();
        if(account.getId() != null) {
            LOGGER.debug("+++ AccountServiceImpl:getById: "+account.toString());
            return Optional.of(this.toDTO(account, account.getClient().getId(), account.getBranchOffice().getId()));
        }
        LOGGER.debug("--- AccountServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    public List<AccountDTO> getAccountByClientId(Long id) {
        // TODO Auto-generated method stub
        List<Account> accounts = accountRepository.findByClientId(id);
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account a : accounts) {
            accountDTOs.add(this.toDTO(a, a.getClient().getId(), a.getBranchOffice().getId()));
        }
        LOGGER.debug("+++ AccountServiceImpl:getAccountByClientId: "+ id);
        return accountDTOs;
    }

    @Override
    public Double getBalanceByClientId(Long id) {
        // TODO Auto-generated method stub
        List<Account> accounts = accountRepository.findByClientId(id);
        Double balance = 0.0;
        for (Account a : accounts) {
            LOGGER.debug("+++ AccountServiceImpl:getBalanceByClientId: ["+a.getBalance()+"]-[" + a.getAccountNumber()+"]");
            balance += a.getBalance();
        }
        LOGGER.debug("+++ AccountServiceImpl:getBalanceByClientId: "+balance+" " + id);
        return balance;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AccountDTO save(AccountPostDTO newAccount, Long id_client, Long id_branchOffice) {
        // TODO Auto-generated method stub
        Account account = this.toEntity(newAccount, id_client, id_branchOffice);
        LOGGER.debug("+++ AccountServiceImpl:save: "+account.toString());
        LOGGER.debug("+++ Es mayor: "+account.getClient().idOfAge());
        if (!account.getClient().idOfAge() && account.getAccountType() == AccountType.CORRIENTE) {
            LOGGER.debug("--- AccountServiceImpl:save: No se puede crear una cuenta CORRIENTE para un cliente menor de edad");
            return null;
        }
        return this.toDTO(accountRepository.save(account), id_client, id_branchOffice);
    }

    public void update(Double amount, Long id) {
        LOGGER.debug("+++ AccountServiceImpl:update: "+amount+" " + id);
		accountRepository.findById(id).map(
                account -> {
                    account.setBeginBalance(account.getBalance());
                    account.setBalance(amount);
                    return accountRepository.save(account);
                }
        );
	}

    public AccountDTO toDTO(Account account, Long id_client, Long id_branchOffice) {
        LOGGER.debug("+++ AccountServiceImpl:toDTO: "+account.toString());
        BranchOfficeDTO branchOffice = branchOfficeServiceImpl.getById(id_branchOffice).get();
        ClientDTO client = clientServiceImpl.getById(id_client).get();
        return new AccountDTO(account.getId(), branchOffice, client, account.getAccountNumber(), account.getBalance(), account.getBeginBalance(), account.getAccountType(), account.getState());
    }

    public Account toEntity (AccountPostDTO accountDTO, Long id_client, Long id_branchOffice) {
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+accountDTO.toString());
        BranchOfficeDTO branchOfficeDTOP = branchOfficeServiceImpl.getById(id_branchOffice).get();
        BranchOffice branchOffice = branchOfficeServiceImpl.toEntity(branchOfficeDTOP, branchOfficeDTOP.getAddress().getId(), branchOfficeDTOP.getEmployee().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+branchOffice.toString());
        ClientDTO clientDTO = clientServiceImpl.getById(id_client).get();
        Client client = clientServiceImpl.toEntity(clientDTO, clientDTO.getAddress().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+client.toString());
        return new Account(branchOffice, client, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    }

    public Account toEntity (AccountDTO accountDTO, Long id_client, Long id_branchOffice) {
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+accountDTO.toString());
        BranchOfficeDTO branchOfficeDTOP = branchOfficeServiceImpl.getById(id_branchOffice).get();
        BranchOffice branchOffice = branchOfficeServiceImpl.toEntity(branchOfficeDTOP, branchOfficeDTOP.getAddress().getId(), branchOfficeDTOP.getEmployee().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+branchOffice.toString());
        ClientDTO clientDTO = clientServiceImpl.getById(id_client).get();
        Client client = clientServiceImpl.toEntity(clientDTO, clientDTO.getAddress().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+client.toString());
        return new Account(accountDTO.getId(), branchOffice, client, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    }

}

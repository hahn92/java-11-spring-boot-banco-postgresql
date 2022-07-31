package com.hahn.banco.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.entity.Account;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.repository.AccountRepository;
import com.hahn.banco.service.IAccountService;


@Service
public class AccountServiceImpl implements IAccountService{
	
    private static final Log LOGGER = LogFactory.getLog(AccountServiceImpl.class);
	
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
	

    public AccountServiceImpl(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }
    

    @Override   
    public Optional<AccountDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Account account = accountRepository.findById(id).get();
        if(account.getId() != null) {
            LOGGER.debug("+++ AccountServiceImpl:getById: "+account.toString());
            return Optional.of(this.toDTO(account));
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
            accountDTOs.add(this.toDTO(a));
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
        Account account = this.toEntity(newAccount);
        LOGGER.debug("+++ AccountServiceImpl:save: "+account.toString());
        LOGGER.debug("+++ Es mayor: "+account.getClient().idOfAge());
        if (!account.getClient().idOfAge() && account.getAccountType() == AccountType.CORRIENTE) {
            LOGGER.debug("--- AccountServiceImpl:save: No se puede crear una cuenta CORRIENTE para un cliente menor de edad");
            return null;
        }
        return this.toDTO(accountRepository.save(account));
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


    public AccountDTO toDTO(Account account) {
        LOGGER.debug("+++ AccountServiceImpl:toDTO: "+account.toString());
        return modelMapper.map(account, AccountDTO.class); 
    }

    public Account toEntity (AccountPostDTO accountDTO) {
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+accountDTO.toString());
        return modelMapper.map(accountDTO, Account.class);
    }

    public Account toEntity (AccountDTO accountDTO) {
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+accountDTO.toString());
        return modelMapper.map(accountDTO, Account.class);
    }

}

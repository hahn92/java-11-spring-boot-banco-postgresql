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
import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.entity.Account;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.service.IAccountService;


@Service
public class AccountServiceImpl implements IAccountService{
	
    private static final Log LOGGER = LogFactory.getLog(AccountServiceImpl.class);
	
    @Autowired
    private IAccountDAO iAccountDAO;
    @Autowired
    private IClientDAO iClientDAO;
    @Autowired
    private IBranchOfficeDAO iBranchOfficeDAO;
	

    public AccountServiceImpl(IAccountDAO iAccountDAO, IClientDAO iClientDAO, IBranchOfficeDAO iBranchOfficeDAO) {
        this.iAccountDAO = iAccountDAO;
        this.iClientDAO = iClientDAO;
        this.iBranchOfficeDAO = iBranchOfficeDAO;
    }

    
    @Override   
    public Optional<AccountDTO> getById(Long id) {
        Account account = iAccountDAO.read(id).get();
        if(account.getId() != null) {
            LOGGER.debug("+++ AccountServiceImpl:getById: "+account.toString());
            AccountDTO accountDTO = iAccountDAO.toDTO(account);
            iClientDAO.read(account.getClient().getId()).ifPresent(client -> accountDTO.setClient(iClientDAO.toDTO(client)));
            iBranchOfficeDAO.read(account.getBranchOffice().getId()).ifPresent(branchOffice -> accountDTO.setBranchOffice(iBranchOfficeDAO.toDTO(branchOffice)));
            return Optional.of(accountDTO);
        }
        LOGGER.debug("--- AccountServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    public List<AccountDTO> getAccountByClientId(Long id) {
        List<Account> accounts = iAccountDAO.list(id);
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOs.add(iAccountDAO.toDTO(account));
        }
        LOGGER.debug("+++ AccountServiceImpl:getAccountByClientId: "+ id);
        return accountDTOs;
    }

    @Override
    public Double getBalanceByClientId(Long id) {
        List<Account> accounts = iAccountDAO.list(id);
        Double balance = 0.0;
        for (Account account : accounts) {
            LOGGER.debug("+++ AccountServiceImpl:getBalanceByClientId: ["+account.getBalance()+"]-[" + account.getAccountNumber()+"]");
            balance += account.getBalance();
        }
        LOGGER.debug("+++ AccountServiceImpl:getBalanceByClientId: "+balance+" " + id);
        return balance;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AccountDTO save(AccountPostDTO newAccount, Long id_client, Long id_branchOffice) {
        Account account = new Account(iBranchOfficeDAO.read(id_branchOffice).get(), iClientDAO.read(id_client).get(), newAccount.getAccountNumber(), newAccount.getBalance(), newAccount.getBalance(), (newAccount.getAccountType()));
        LOGGER.debug("+++ AccountServiceImpl:save: "+account.toString());
        LOGGER.debug("+++ Es mayor: "+account.getClient().idOfAge());
        if (!account.getClient().idOfAge() && account.getAccountType() == AccountType.CORRIENTE) {
            LOGGER.debug("--- AccountServiceImpl:save: No se puede crear una cuenta CORRIENTE para un cliente menor de edad");
            return null;
        }
        return iAccountDAO.toDTO(iAccountDAO.create(account));
    }

    public void update(Double amount, Long id) {
        LOGGER.debug("+++ AccountServiceImpl:update: "+amount+" " + id);
        Account account = iAccountDAO.read(id).get();
		iAccountDAO.update(account, amount, id);
	}

}

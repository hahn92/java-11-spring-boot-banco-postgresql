package com.hahn.banco.service.implement;

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
    @Transactional(rollbackOn = Exception.class)
    public AccountDTO save(AccountPostDTO newAccount, Long id_client, Long id_branchOffice) {
        // TODO Auto-generated method stub
        Account account = this.toEntity(newAccount, id_client, id_branchOffice);
        LOGGER.debug("+++ AccountServiceImpl:save: "+account.toString());
        return this.toDTO(accountRepository.save(account), id_client, id_branchOffice);
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
        BranchOffice branchOffice = branchOfficeServiceImpl.toEntity(branchOfficeDTOP, branchOfficeDTOP.getEmployee().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+branchOffice.toString());
        ClientDTO clientDTO = clientServiceImpl.getById(id_client).get();
        Client client = clientServiceImpl.toEntity(clientDTO, clientDTO.getAddress().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+client.toString());
        return new Account(branchOffice, client, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    }

    public Account toEntity (AccountDTO accountDTO, Long id_client, Long id_branchOffice) {
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+accountDTO.toString());
        BranchOfficeDTO branchOfficeDTOP = branchOfficeServiceImpl.getById(id_branchOffice).get();
        BranchOffice branchOffice = branchOfficeServiceImpl.toEntity(branchOfficeDTOP, branchOfficeDTOP.getEmployee().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+branchOffice.toString());
        ClientDTO clientDTO = clientServiceImpl.getById(id_client).get();
        Client client = clientServiceImpl.toEntity(clientDTO, clientDTO.getAddress().getId());
        LOGGER.debug("+++ AccountServiceImpl:toEntity: "+client.toString());
        return new Account(accountDTO.getId(), branchOffice, client, accountDTO.getAccountNumber(), accountDTO.getBalance(), accountDTO.getBeginBalance(), accountDTO.getAccountType()); 
    }


}

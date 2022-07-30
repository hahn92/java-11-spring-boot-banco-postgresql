package com.hahn.banco.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.account.AccountPostDTO;
import com.hahn.banco.service.IAccountService;



@RestController
@RequestMapping("api/v1")
public class AccountController {
	
    private static final Log LOGGER = LogFactory.getLog(AccountController.class);

	@Autowired
    private IAccountService iAccountService;

	public AccountController(IAccountService iAccountService) {
		this.iAccountService = iAccountService;
	}

	@GetMapping("account/{id}")
	public ResponseEntity<Optional<AccountDTO>> AccountGetById(@PathVariable Long id) {
		LOGGER.debug("+++ AccountGetById: "+id);
		try {
			return new ResponseEntity<>(iAccountService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("AccountGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("client/{id}/account/")
	public ResponseEntity<Double> BalanceGetById(@PathVariable Long id) {
		LOGGER.debug("+++ BalanceGetById: "+id);
		try {
			return new ResponseEntity<>(iAccountService.getBalanceById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("BalanceGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("client/{id_client}/branchOffice/{id_branchOffice}/account/")
	public ResponseEntity<AccountDTO> AccountSave(@PathVariable Long id_client, @PathVariable Long id_branchOffice, @Valid AccountPostDTO newAccount) {
		LOGGER.debug("+++ AccountSave: "+newAccount.toString());
		try {
			return new ResponseEntity<>(iAccountService.save(newAccount, id_client, id_branchOffice), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Cuenta: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

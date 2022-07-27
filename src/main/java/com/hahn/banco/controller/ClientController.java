package com.hahn.banco.controller;

import java.util.Optional;

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

import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.service.IClientService;


@RestController
@RequestMapping("api/v1")
public class ClientController {
	
    private static final Log LOGGER = LogFactory.getLog(ClientController.class);

	@Autowired
    private IClientService iClientService;

	public ClientController(IClientService iClientService) {
		this.iClientService = iClientService;
	}

	@GetMapping("client/{id}")
	public ResponseEntity<Optional<ClientDTO>> ClientGetById(@PathVariable Long id) {
		LOGGER.debug("+++ ClientGetById: "+id);
		try {
			return new ResponseEntity<>(iClientService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("ClientGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("client/sing-up")
	public ResponseEntity<ClientDTO> ClientSave(ClientPostDTO newClient) {
		LOGGER.debug("+++ ClientSave: "+newClient.toString());
		try {
			return new ResponseEntity<>(iClientService.save(newClient), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Usuario: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

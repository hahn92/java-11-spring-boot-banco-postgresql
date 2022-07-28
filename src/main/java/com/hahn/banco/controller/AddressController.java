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

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.address.AddressPostDTO;
import com.hahn.banco.service.IAddressService;



@RestController
@RequestMapping("api/v1")
public class AddressController {
	
    private static final Log LOGGER = LogFactory.getLog(AddressController.class);

	@Autowired
    private IAddressService iAddressService;

	public AddressController(IAddressService iAddressService) {
		this.iAddressService = iAddressService;
	}

	@GetMapping("address/{id}")
	public ResponseEntity<Optional<AddressDTO>> AddressGetById(@PathVariable Long id) {
		LOGGER.debug("+++ AddressGetById: "+id);
		try {
			return new ResponseEntity<>(iAddressService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("AddressGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("city/{id_address}/address/")
	public ResponseEntity<AddressDTO> AddressSave(@PathVariable Long id_address, @Valid AddressPostDTO newAddress) {
		LOGGER.debug("+++ AddressSave: "+newAddress.toString());
		try {
			return new ResponseEntity<>(iAddressService.save(newAddress, id_address), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Direcciôn: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

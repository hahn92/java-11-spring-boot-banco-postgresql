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

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.service.IBranchOfficeService;


@RestController
@RequestMapping("api/v1")
public class BranchOfficeController {
	
    private static final Log LOGGER = LogFactory.getLog(BranchOfficeController.class);

	@Autowired
    private IBranchOfficeService iBranchOfficeService;

	public BranchOfficeController(IBranchOfficeService iBranchOfficeService) {
		this.iBranchOfficeService = iBranchOfficeService;
	}

	@GetMapping("branchOffice/{id}")
	public ResponseEntity<Optional<BranchOfficeDTO>> BranchOfficeGetById(@PathVariable Long id) {
		LOGGER.debug("+++ BranchOfficeGetById: "+id);
		try {
			return new ResponseEntity<>(iBranchOfficeService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("BranchOfficeGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("employee/{id_employee}/branchOffice/")
	public ResponseEntity<BranchOfficeDTO> BranchOfficeSave(@PathVariable Long id_employee, @Valid BranchOfficePostDTO newBranchOffice) {
		LOGGER.debug("+++ BranchOfficeSave: "+newBranchOffice.toString());
		try {
			return new ResponseEntity<>(iBranchOfficeService.save(newBranchOffice, id_employee), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Sucursal: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

package com.hahn.banco.controller;

import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;
import com.hahn.banco.service.IOperationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1")
public class OperationController {

    private static final Log LOGGER = LogFactory.getLog(OperationController.class);

	@Autowired
    private IOperationService iOperationService;

	public OperationController(IOperationService iOperationService) {
		this.iOperationService = iOperationService;
	}

	@GetMapping("operation/{id}")
	public ResponseEntity<Optional<OperationDTO>> OperationGetById(@PathVariable Long id) {
		LOGGER.debug("+++ OperationGetById: "+id);
		try {
			return new ResponseEntity<>(iOperationService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("OperationGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("account/{id}/operation/")
	public ResponseEntity<List<OperationDTO>> OperationGetByUserId(@PathVariable Long id) {
		LOGGER.debug("+++ OperationGetByUserId: "+id);
		try {
			return new ResponseEntity<>(iOperationService.getOperationByAccountId(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("OperationGetByUserId: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("client/{id_client1}/client/{id_client2}/operation/")
	public ResponseEntity<Boolean> OperationCreate(@PathVariable Long id_client1, @PathVariable Long id_client2, @Valid OperationPostDTO newOperation) {
		LOGGER.debug("+++ OperationCreate: "+newOperation.toString());
		try {
			return new ResponseEntity<>(iOperationService.operation(newOperation, id_client1, id_client2), HttpStatus.OK);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Operacion: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

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

import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;
import com.hahn.banco.service.IDepartmentService;


@RestController
@RequestMapping("api/v1")
public class DepartmentController {
	
    private static final Log LOGGER = LogFactory.getLog(DepartmentController.class);

	@Autowired
    private IDepartmentService iDepartmentService;

	public DepartmentController(IDepartmentService iDepartmentService) {
		this.iDepartmentService = iDepartmentService;
	}

	@GetMapping("department/{id}")
	public ResponseEntity<Optional<DepartmentDTO>> DepartamentGetById(@PathVariable Long id) {
		LOGGER.debug("+++ DepartamentGetById: "+id);
		try {
			return new ResponseEntity<>(iDepartmentService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("DepartamentGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("department/")
	public ResponseEntity<DepartmentDTO> DepartmentSave(DepartmentPostDTO newClient) {
		LOGGER.debug("+++ DepartmentSave: "+newClient.toString());
		try {
			return new ResponseEntity<>(iDepartmentService.save(newClient), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Departamento: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

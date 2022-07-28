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

import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.service.IEmployeeService;


@RestController
@RequestMapping("api/v1")
public class EmployeeController {
	
    private static final Log LOGGER = LogFactory.getLog(EmployeeController.class);

	@Autowired
    private IEmployeeService iEmployeeService;

	public EmployeeController(IEmployeeService iEmployeeService) {
		this.iEmployeeService = iEmployeeService;
	}

	@GetMapping("employee/{id}")
	public ResponseEntity<Optional<EmployeeDTO>> EmployeeGetById(@PathVariable Long id) {
		LOGGER.debug("+++ EmployeeGetById: "+id);
		try {
			return new ResponseEntity<>(iEmployeeService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("EmployeeGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("address/{id_address}/employee/")
	public ResponseEntity<EmployeeDTO> EmployeeSave(@PathVariable Long id_address, @Valid EmployeePostDTO newEmployee) {
		LOGGER.debug("+++ EmployeeSave: "+newEmployee.toString());
		try {
			return new ResponseEntity<>(iEmployeeService.save(newEmployee, id_address), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Empleado: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

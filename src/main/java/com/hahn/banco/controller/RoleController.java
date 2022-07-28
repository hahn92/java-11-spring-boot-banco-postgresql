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

import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;
import com.hahn.banco.service.IRoleService;



@RestController
@RequestMapping("api/v1")
public class RoleController {
	
    private static final Log LOGGER = LogFactory.getLog(RoleController.class);

	@Autowired
    private IRoleService iRoleService;

	public RoleController(IRoleService iRoleService) {
		this.iRoleService = iRoleService;
	}

	@GetMapping("role/{id}")
	public ResponseEntity<Optional<RoleDTO>> RoleGetById(@PathVariable Long id) {
		LOGGER.debug("+++ RoleGetById: "+id);
		try {
			return new ResponseEntity<>(iRoleService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("RoleGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("role/")
	public ResponseEntity<RoleDTO> RoleSave(@Valid RolePostDTO newRole) {
		LOGGER.debug("+++ RoleSave: "+newRole.toString());
		try {
			return new ResponseEntity<>(iRoleService.save(newRole), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Rol: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

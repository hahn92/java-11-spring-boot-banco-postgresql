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

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.service.ICityService;


@RestController
@RequestMapping("api/v1")
public class CityController {
	
    private static final Log LOGGER = LogFactory.getLog(CityController.class);

	@Autowired
    private ICityService iCityService;

	public CityController(ICityService iCityService) {
		this.iCityService = iCityService;
	}

	@GetMapping("city/{id}")
	public ResponseEntity<Optional<CityDTO>> CityGetById(@PathVariable Long id) {
		LOGGER.debug("+++ CityGetById: "+id);
		try {
			return new ResponseEntity<>(iCityService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("CityGetById: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("department/{id_department}/city/")
	public ResponseEntity<CityDTO> CitySave(@PathVariable Long id_department, @Valid CityPostDTO newCity) {
		LOGGER.debug("+++ CitySave: "+newCity.toString());
		try {
			return new ResponseEntity<>(iCityService.save(newCity, id_department), HttpStatus.CREATED);
		}catch (Exception e){
			LOGGER.error("Rollback error registro Ciudad: "+e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

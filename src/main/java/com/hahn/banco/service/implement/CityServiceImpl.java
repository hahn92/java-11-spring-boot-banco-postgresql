package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.entity.City;
import com.hahn.banco.entity.Department;
import com.hahn.banco.repository.CityRepository;
import com.hahn.banco.repository.DepartmentRepository;
import com.hahn.banco.service.ICityService;


@Service
public class CityServiceImpl implements ICityService{
	
    private static final Log LOGGER = LogFactory.getLog(CityServiceImpl.class);
	
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
	
	public CityServiceImpl(CityRepository cityRepository, DepartmentRepository departmentService) {
        this.cityRepository = cityRepository;
        this.departmentRepository = departmentService;
    }

    @Override   
    public Optional<CityDTO> getById(Long id) {
        // TODO Auto-generated method stub
        City city = cityRepository.findById(id).get();
        if(city.getId() == null) {
            LOGGER.debug("+++ getById: "+city.toString());
            return Optional.of(this.toDTO(city, city.getDepartment().getId()));
        }
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CityDTO save(CityPostDTO newCity, long id_department) {
        // TODO Auto-generated method stub
        City city = this.toEntity(newCity, id_department);
        LOGGER.debug("+++ save: "+city.toString());
        return this.toDTO(cityRepository.save(city), id_department);
    }


    private CityDTO toDTO(City city, long id_department) {
        LOGGER.debug("+++ toDTO: "+city.toString());
        return new CityDTO(city.getId(), this.toDTO(city.getDepartment()), city.getName(),  city.getState());
    }

    private City toEntity (CityPostDTO cityDTO, long id_department) {
        LOGGER.debug("+++ toEntity: "+cityDTO.toString());
        Department department = departmentRepository.findById(id_department).get();
        LOGGER.debug("+++ toEntity: "+department.toString());
        return new City(department, cityDTO.getName());   
    }

    private DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getState());
    }


}

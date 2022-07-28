package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.entity.City;
import com.hahn.banco.entity.Department;
import com.hahn.banco.repository.CityRepository;
import com.hahn.banco.service.ICityService;


@Service
public class CityServiceImpl implements ICityService{
	
    private static final Log LOGGER = LogFactory.getLog(CityServiceImpl.class);
	
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private DepartmentServiceImpl departmentServiceImpl;
	
	public CityServiceImpl(CityRepository cityRepository, DepartmentServiceImpl departmentServiceImpl) {
        this.cityRepository = cityRepository;
        this.departmentServiceImpl = departmentServiceImpl;
    }

    @Override   
    public Optional<CityDTO> getById(Long id) {
        // TODO Auto-generated method stub
        City city = cityRepository.findById(id).get();
        if(city.getId() != null) {
            LOGGER.debug("+++ CityServiceImpl:getById: "+city.toString());
            return Optional.of(this.toDTO(city, city.getDepartment().getId()));
        }
        LOGGER.debug("--- CityServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CityDTO save(CityPostDTO newCity, Long id_department) {
        // TODO Auto-generated method stub
        City city = this.toEntity(newCity, id_department);
        LOGGER.debug("+++ CityServiceImpl:save: "+city.toString());
        return this.toDTO(cityRepository.save(city), id_department);
    }


    public CityDTO toDTO(City city, Long id_department) {
        LOGGER.debug("+++ CityServiceImpl:toDTO: "+city.toString());
        return new CityDTO(city.getId(), departmentServiceImpl.getById(id_department).get(), city.getName(), city.getState());
    }

    public City toEntity (CityPostDTO cityDTO, Long id_department) {
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+cityDTO.toString());
        Department department = departmentServiceImpl.toEntity(departmentServiceImpl.getById(id_department).get());
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+department.toString());
        return new City(department, cityDTO.getName());   
    }

    public City toEntity (CityDTO cityDTO, Long id_department) {
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+cityDTO.toString());
        Department department = departmentServiceImpl.toEntity(departmentServiceImpl.getById(id_department).get());
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+department.toString());
        return new City(cityDTO.getId(), department, cityDTO.getName());   
    }


}

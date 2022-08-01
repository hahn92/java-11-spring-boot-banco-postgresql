package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.ICityDAO;
import com.hahn.banco.dao.IDepartmentDAO;
import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.entity.City;
import com.hahn.banco.service.ICityService;


@Service
public class CityServiceImpl implements ICityService{
	
    private static final Log LOGGER = LogFactory.getLog(CityServiceImpl.class);
	
    @Autowired
    private ICityDAO iCityDAO;
    @Autowired
    private IDepartmentDAO iDepartmentDAO;
	

    public CityServiceImpl(ICityDAO iCityDAO, IDepartmentDAO iDepartmentDAO) {
        this.iCityDAO = iCityDAO;
        this.iDepartmentDAO = iDepartmentDAO;
    }

    
    @Override   
    public Optional<CityDTO> getById(Long id) {
        City city = iCityDAO.read(id).get();
        if(city.getId() != null) {
            LOGGER.debug("+++ CityServiceImpl:getById: "+city.toString());
            CityDTO cityDTO = iCityDAO.toDTO(city);
            iDepartmentDAO.read(city.getDepartment().getId()).ifPresent(department -> cityDTO.setDepartment(iDepartmentDAO.toDTO(department)));
            return Optional.of(cityDTO);
        }
        LOGGER.debug("--- CityServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CityDTO save(CityPostDTO newCity, Long id_department) {
        City city = iCityDAO.toEntity(newCity);
        LOGGER.debug("+++ CityServiceImpl:save: "+city.toString());
        iDepartmentDAO.read(id_department).ifPresent(department -> city.setDepartment(department));
        return iCityDAO.toDTO(iCityDAO.create(city));
    }


}

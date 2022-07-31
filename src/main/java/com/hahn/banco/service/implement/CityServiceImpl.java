package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.entity.City;
import com.hahn.banco.repository.CityRepository;
import com.hahn.banco.service.ICityService;


@Service
public class CityServiceImpl implements ICityService{
	
    private static final Log LOGGER = LogFactory.getLog(CityServiceImpl.class);
	
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;
	

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    
    @Override   
    public Optional<CityDTO> getById(Long id) {
        // TODO Auto-generated method stub
        City city = cityRepository.findById(id).get();
        if(city.getId() != null) {
            LOGGER.debug("+++ CityServiceImpl:getById: "+city.toString());
            return Optional.of(this.toDTO(city));
        }
        LOGGER.debug("--- CityServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public CityDTO save(CityPostDTO newCity, Long id_department) {
        // TODO Auto-generated method stub
        City city = this.toEntity(newCity);
        LOGGER.debug("+++ CityServiceImpl:save: "+city.toString());
        return this.toDTO(cityRepository.save(city));
    }


    public CityDTO toDTO(City city) {
        LOGGER.debug("+++ CityServiceImpl:toDTO: "+city.toString());
        return modelMapper.map(city, CityDTO.class);
    }

    public City toEntity (CityPostDTO cityDTO) {
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+cityDTO.toString());
        return modelMapper.map(cityDTO, City.class);
    }

    public City toEntity (CityDTO cityDTO) {
        LOGGER.debug("+++ CityServiceImpl:toEntity: "+cityDTO.toString());
        return modelMapper.map(cityDTO, City.class);
    }

}

package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;

public interface ICityService {

    Optional<CityDTO> getById(Long id);

    CityDTO save(CityPostDTO newCity, Long id_department);
    
}

package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.entity.City;

public interface ICityDAO {

    Optional<City> read(Long id);

    List<City> list(String name);

    City create(City newCity);
    
    void update(City newCity, Long id);
    
    void delete(Long id);

    CityDTO toDTO(City city);

    City toEntity(CityPostDTO newCity);

    City toEntity(CityDTO newCity);

}

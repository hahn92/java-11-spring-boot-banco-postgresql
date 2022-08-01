package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.ICityDAO;
import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.dto.city.CityPostDTO;
import com.hahn.banco.entity.City;
import com.hahn.banco.repository.CityRepository;

@Component
public class CityDAO implements ICityDAO {

    @Autowired
    private CityRepository cityRepository;
	

    @Override
    public City create(City newCity) {
        return cityRepository.save(newCity);
    }

    @Override
    public void delete(Long id) {
        cityRepository.deleteById(id);   
    }

    @Override
    public List<City> list(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public Optional<City> read(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public void update(City newCity, Long id) {
        this.read(id).map(
                city -> {
                    city.setName(newCity.getName());
                    cityRepository.save(city);
                    return true;
                }
        );
    }

    @Override
    public CityDTO toDTO(City city) {
        return new CityDTO(city.getId(), null, city.getName(), city.getState());
    }

    @Override
    public City toEntity (CityPostDTO cityDTO) {
        return new City(null, cityDTO.getName());   
    }

    @Override
    public City toEntity (CityDTO cityDTO) {
        return new City(cityDTO.getId(), null, cityDTO.getName());   
    }

    
}

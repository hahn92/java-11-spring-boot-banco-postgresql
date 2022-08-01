package com.hahn.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.City;

@Repository
public interface CityRepository extends PagingAndSortingRepository<City, Integer> {

    Optional<City> findById(Long id);

    void deleteById(Long id);

    List<City> findByName(String name);
    
}

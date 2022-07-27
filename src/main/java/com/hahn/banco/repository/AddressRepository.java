package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Address;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {

    Optional<Address> findById(Long id);
    
}

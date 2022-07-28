package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Role;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

    Optional<Role> findById(Long id);
    
}

package com.hahn.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Role;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

    Optional<Role> findById(Long id);

    List<Role> findByName(String name);

    void deleteById(Long id);
    
}

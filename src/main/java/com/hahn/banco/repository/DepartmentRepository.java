package com.hahn.banco.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Department;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer> {

    Optional<Department> findById(Long id);

    void deleteById(Long id);

    List<Department> findByName(String name);
    
}

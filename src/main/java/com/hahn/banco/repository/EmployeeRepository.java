package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Employee;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {

    Optional<Employee> findById(Long id);
    
}

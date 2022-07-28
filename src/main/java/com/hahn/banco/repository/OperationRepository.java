package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Operation;

@Repository
public interface OperationRepository extends PagingAndSortingRepository<Operation, Integer> {

    Optional<Operation> findById(Long id);
    
}

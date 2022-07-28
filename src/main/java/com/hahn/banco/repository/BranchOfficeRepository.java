package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.BranchOffice;

@Repository
public interface BranchOfficeRepository extends PagingAndSortingRepository<BranchOffice, Integer> {

    Optional<BranchOffice> findById(Long id);
    
}

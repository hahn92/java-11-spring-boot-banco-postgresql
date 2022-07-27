package com.hahn.banco.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hahn.banco.entity.Client;


@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {
	
	Client findByUsername(String username);

    Optional<Client> findById(Long id);

    void deleteById(Long id);
	
}

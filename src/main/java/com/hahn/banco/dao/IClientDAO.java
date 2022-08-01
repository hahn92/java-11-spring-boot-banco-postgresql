package com.hahn.banco.dao;

import java.util.Optional;

import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.entity.Client;

public interface IClientDAO {

    Optional<Client> read(Long id);

    Optional<Client> readByUsername(String name);

    Client create(Client newClient);
    
    void update(Client newClient, Long id);
    
    void delete(Long id);

    ClientDTO toDTO(Client client);

    Client toEntity(ClientPostDTO newClient);

    Client toEntity(ClientDTO newClient);

}

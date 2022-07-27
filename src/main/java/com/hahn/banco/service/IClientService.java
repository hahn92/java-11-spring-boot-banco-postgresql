package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;

public interface IClientService {

    Optional<ClientDTO> getById(Long id);

    ClientDTO save(ClientPostDTO newClient);
    
}

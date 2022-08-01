package com.hahn.banco.dao.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IClientDAO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.entity.Client;
import com.hahn.banco.repository.ClientRepository;

@Component
public class ClientDAO implements IClientDAO {

    @Autowired
    private ClientRepository clientRepository;
	

    @Override
    public Client create(Client newCity) {
        return clientRepository.save(newCity);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);   
    }

    @Override
    public Optional<Client> readByUsername(String name) {
        return clientRepository.findByUsername(name);
    }

    @Override
    public Optional<Client> read(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void update(Client newCity, Long id) {
        this.read(id).map(
                city -> {
                    city.setName(newCity.getName());
                    clientRepository.save(city);
                    return true;
                }
        );
    }

    @Override
    public ClientDTO toDTO(Client client) {
        return new ClientDTO(client.getId(), client.getName(), client.getSurname(), client.getUsername(), client.getEmail(), client.getBirthdate(), client.getTelephone(), client.getDocumentType(), client.getDocument(), null, client.getState());
    }

    @Override
    public Client toEntity (ClientPostDTO clientDTO) {
        return new Client(null, clientDTO.getName(), clientDTO.getSurname(), clientDTO.getTelephone(), clientDTO.getDocumentType(), clientDTO.getDocument(), clientDTO.getBirthdate(), clientDTO.getUsername(), clientDTO.getEmail(), clientDTO.getPassword());  
    }

    @Override
    public Client toEntity (ClientDTO clientDTO) {
        return new Client(clientDTO.getId(), null, clientDTO.getName(), clientDTO.getSurname(), clientDTO.getTelephone(), clientDTO.getDocumentType(), clientDTO.getDocument(), clientDTO.getBirthdate(), clientDTO.getUsername(), clientDTO.getEmail(), "123"); 
    }

}

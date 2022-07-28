package com.hahn.banco.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.entity.Client;
import com.hahn.banco.repository.ClientRepository;
import com.hahn.banco.service.IClientService;


@Service
public class ClientServiceImpl implements UserDetailsService, IClientService {
	
    private static final Log LOGGER = LogFactory.getLog(ClientServiceImpl.class);
	
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AddressServiceImpl addressServiceImpl;
	
	public ClientServiceImpl(ClientRepository clientRepository, AddressServiceImpl addressServiceImpl) {
        this.clientRepository = clientRepository;
        this.addressServiceImpl = addressServiceImpl;
    }

    @Override   
    public Optional<ClientDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Client client = clientRepository.findById(id).get();
        if(client.getId() != null) {
            LOGGER.debug("+++ ClientServiceImpl:getById: "+client.toString());
            return Optional.of(this.toDTO(client, client.getAddress().getId()));
        }
        LOGGER.debug("--- ClientServiceImpl:getById: No se encontro el cliente con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClientDTO save(ClientPostDTO newClient, Long id_address) {
        // TODO Auto-generated method stub
        Client client = this.toEntity(newClient, id_address); 
        LOGGER.debug("+++ ClientServiceImpl:save: "+client.toString());
        return this.toDTO(clientRepository.save(client), id_address);
    }

	/* 
	 * funcion para login con JWT	
	*/
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client us = clientRepository.findByUsername(username);
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		
		UserDetails userDet = new User(us.getUsername(), us.getPassword(), roles);
		
		return userDet;
		 
	}

    public ClientDTO toDTO(Client client, Long id_address) {
        LOGGER.debug("+++ ClientServiceImpl:toDTO: "+client.toString());
        return new ClientDTO(client.getId(), client.getName(), client.getSurname(), client.getUsername(), client.getEmail(), client.getBirthdate(), client.getTelephone(), client.getDocumentType(), client.getDocument(), addressServiceImpl.getById(id_address).get(), client.getState());
    }

    public Client toEntity (ClientPostDTO clientDTO, Long id_address) {
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+clientDTO.toString());
        AddressDTO addressDTO = addressServiceImpl.getById(id_address).get();
        Address address = addressServiceImpl.toEntity(addressDTO, addressDTO.getCity().getId());
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+address.toString());
        return new Client(address, clientDTO.getName(), clientDTO.getSurname(), clientDTO.getTelephone(), clientDTO.getDocumentType(), clientDTO.getDocument(), clientDTO.getBirthdate(), clientDTO.getUsername(), clientDTO.getEmail(), clientDTO.getPassword());   
    }

    public Client toEntity (ClientDTO clientDTO, Long id_address) {
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+clientDTO.toString());
        AddressDTO addressDTO = addressServiceImpl.getById(id_address).get();
        Address address = addressServiceImpl.toEntity(addressDTO, addressDTO.getCity().getId());
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+address.toString());
        return new Client(clientDTO.getId(), address, clientDTO.getName(), clientDTO.getSurname(), clientDTO.getTelephone(), clientDTO.getDocumentType(), clientDTO.getDocument(), clientDTO.getBirthdate(), clientDTO.getUsername(), clientDTO.getEmail(), "123456789");    
    }

}

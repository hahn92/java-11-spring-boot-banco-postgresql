package com.hahn.banco.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.entity.Client;
import com.hahn.banco.repository.ClientRepository;
import com.hahn.banco.service.IClientService;


@Service
public class ClientServiceImpl implements UserDetailsService, IClientService {
	
    private static final Log LOGGER = LogFactory.getLog(ClientServiceImpl.class);
	
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ModelMapper modelMapper;
	

	public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    
    @Override   
    public Optional<ClientDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Client client = clientRepository.findById(id).get();
        if(client.getId() != null) {
            LOGGER.debug("+++ ClientServiceImpl:getById: "+client.toString());
            return Optional.of(this.toDTO(client));
        }
        LOGGER.debug("--- ClientServiceImpl:getById: No se encontro el cliente con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClientDTO save(ClientPostDTO newClient, Long id_address) {
        // TODO Auto-generated method stub
        Client client = this.toEntity(newClient); 
        LOGGER.debug("+++ ClientServiceImpl:save: "+client.toString());
        return this.toDTO(clientRepository.save(client));
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


    public ClientDTO toDTO(Client client) {
        LOGGER.debug("+++ ClientServiceImpl:toDTO: "+client.toString());
        return modelMapper.map(client, ClientDTO.class);
    }

    public Client toEntity (ClientPostDTO clientDTO) {
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+clientDTO.toString());
        return modelMapper.map(clientDTO, Client.class); 
    }

    public Client toEntity (ClientDTO clientDTO) {
        LOGGER.debug("+++ ClientServiceImpl:toEntity: "+clientDTO.toString());
        return modelMapper.map(clientDTO, Client.class);     
    }

}

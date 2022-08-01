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

import com.hahn.banco.dao.IAddressDAO;
import com.hahn.banco.dao.IClientDAO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.dto.client.ClientPostDTO;
import com.hahn.banco.entity.Client;
import com.hahn.banco.service.IClientService;


@Service
public class ClientServiceImpl implements UserDetailsService, IClientService {
	
    private static final Log LOGGER = LogFactory.getLog(ClientServiceImpl.class);
	
    @Autowired
    private IClientDAO iClientDAO;
    @Autowired
    private IAddressDAO iAddressDAO;
	

	public ClientServiceImpl(IClientDAO iClientDAO, IAddressDAO iAddressDAO) {
        this.iClientDAO = iClientDAO;
        this.iAddressDAO = iAddressDAO;
    }
    

    @Override   
    public Optional<ClientDTO> getById(Long id) {
        Client client = iClientDAO.read(id).get();
        if(client.getId() != null) {
            LOGGER.debug("+++ ClientServiceImpl:getById: "+client.toString());
            ClientDTO clientDTO = iClientDAO.toDTO(client);
            iAddressDAO.read(client.getAddress().getId()).ifPresent(department -> clientDTO.setAddress(iAddressDAO.toDTO(department)));
            return Optional.of(clientDTO);
        }
        LOGGER.debug("--- ClientServiceImpl:getById: No se encontro el cliente con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClientDTO save(ClientPostDTO newClient, Long id_address) {
        Client client = iClientDAO.toEntity(newClient); 
        LOGGER.debug("+++ ClientServiceImpl:save: "+client.toString());
        iAddressDAO.read(id_address).ifPresent(address -> client.setAddress(address));
        return iClientDAO.toDTO(iClientDAO.create(client));
    }

	/* 
	 * funcion para login con JWT	
	*/
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Client client = iClientDAO.readByUsername(username).get();
		
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ADMIN"));
		
		UserDetails userDet = new User(client.getUsername(), client.getPassword(), roles);
		
		return userDet;
		 
	}

}

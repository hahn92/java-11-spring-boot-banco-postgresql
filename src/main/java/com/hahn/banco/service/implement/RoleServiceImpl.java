package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;
import com.hahn.banco.entity.Role;
import com.hahn.banco.repository.RoleRepository;
import com.hahn.banco.service.IRoleService;


@Service
public class RoleServiceImpl implements IRoleService {
	
    private static final Log LOGGER = LogFactory.getLog(RoleServiceImpl.class);
	
    @Autowired
    private RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override   
    public Optional<RoleDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Role role = roleRepository.findById(id).get();
        if(role.getId() != null) {
            LOGGER.debug("+++ RoleServiceImpl:getById: "+role.toString());
            return Optional.of(this.toDTO(role));
        }
        LOGGER.debug("--- RoleServiceImpl:getById: No existe el rol con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoleDTO save(RolePostDTO newRole) {
        // TODO Auto-generated method stub
        Role role = this.toEntity(newRole); 
        LOGGER.debug("+++ RoleServiceImpl:save: "+role.toString());
        return this.toDTO(roleRepository.save(role));
    }


    public RoleDTO toDTO(Role role) {
        LOGGER.debug("+++ RoleServiceImpl:toDTO: "+role.toString());
        return new RoleDTO(role.getId(), role.getName(), role.getState());
    }

    public Role toEntity (RolePostDTO roleDTO) {
        LOGGER.debug("+++ RoleServiceImpl:toEntity: "+roleDTO.toString());
        return new Role(roleDTO.getName());   
    }

    public Role toEntity (RoleDTO roleDTO) {
        LOGGER.debug("+++ RoleServiceImpl:toEntity: "+roleDTO.toString());
        return new Role(roleDTO.getId(), roleDTO.getName());   
    }
    

}

package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IRoleDAO;
import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;
import com.hahn.banco.entity.Role;
import com.hahn.banco.service.IRoleService;


@Service
public class RoleServiceImpl implements IRoleService {
	
    private static final Log LOGGER = LogFactory.getLog(RoleServiceImpl.class);
	
    @Autowired
    private IRoleDAO iRoleDAO;
	

	public RoleServiceImpl(IRoleDAO iRoleDAO) {
        this.iRoleDAO = iRoleDAO;
    }
    

    @Override   
    public Optional<RoleDTO> getById(Long id) {
        Role role = iRoleDAO.read(id).get();
        if(role.getId() != null) {
            LOGGER.debug("+++ RoleServiceImpl:getById: "+role.toString());
            return Optional.of(iRoleDAO.toDTO(role));
        }
        LOGGER.debug("--- RoleServiceImpl:getById: No existe el rol con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RoleDTO save(RolePostDTO newRole) {
        Role role = iRoleDAO.toEntity(newRole); 
        LOGGER.debug("+++ RoleServiceImpl:save: "+role.toString());
        return iRoleDAO.toDTO(iRoleDAO.create(role));
    }
    
}

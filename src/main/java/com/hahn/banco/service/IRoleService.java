package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;

public interface IRoleService {

    Optional<RoleDTO> getById(Long id);

    RoleDTO save(RolePostDTO newRole);
    
}

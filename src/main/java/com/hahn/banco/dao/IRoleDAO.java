package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;
import com.hahn.banco.entity.Role;


public interface IRoleDAO {

    Optional<Role> read(Long id);

    List<Role> list(String name);

    Role create(Role newRole);
    
    void update(Role newRole, Long id);
    
    void delete(Long id);

    RoleDTO toDTO(Role Role);

    Role toEntity(RolePostDTO newRole);

    Role toEntity(RoleDTO newRole);

}

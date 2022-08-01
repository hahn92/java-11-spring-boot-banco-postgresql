package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IRoleDAO;
import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.dto.role.RolePostDTO;
import com.hahn.banco.entity.Role;
import com.hahn.banco.repository.RoleRepository;

@Component
public class RoleDAO implements IRoleDAO {

    @Autowired
    private RoleRepository roleRepository;
	

    @Override
    public Role create(Role newRole) {
        return roleRepository.save(newRole);
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);   
    }

    @Override
    public List<Role> list(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Optional<Role> read(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void update(Role newRole, Long id) {
        this.read(id).map(
            role -> {
                    role.setName(newRole.getName());
                    roleRepository.save(role);
                    return true;
                }
        );
    }

    @Override
    public RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getState());
    }

    @Override
    public Role toEntity (RolePostDTO roleDTO) {
        return new Role(roleDTO.getName());   
    }

    @Override
    public Role toEntity (RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getName());   
    }
    
}

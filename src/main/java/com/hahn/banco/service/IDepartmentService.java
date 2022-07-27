package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;

public interface IDepartmentService {

    Optional<DepartmentDTO> getById(Long id);

    DepartmentDTO save(DepartmentPostDTO newClient);
    
}

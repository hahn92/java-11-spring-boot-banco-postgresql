package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;

public interface IEmployeeService {

    Optional<EmployeeDTO> getById(Long id);

    EmployeeDTO save(EmployeePostDTO newEmployee, Long id_address);
    
}

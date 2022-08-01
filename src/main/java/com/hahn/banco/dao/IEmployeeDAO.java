package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.entity.Employee;

public interface IEmployeeDAO {

    Optional<Employee> read(Long id);

    List<Employee> list(String name);

    Employee create(Employee newEmployee);
    
    void update(Employee newEmployee, Long id);
    
    void delete(Long id);

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeePostDTO newEmployee);

    Employee toEntity(EmployeeDTO newEmployee);

}

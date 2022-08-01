package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;
import com.hahn.banco.entity.Department;

public interface IDepartmentDAO {

    Optional<Department> read(Long id);

    List<Department> list(String name);

    Department create(Department newDepartment);
    
    void update(Department newDepartment, Long id);
    
    void delete(Long id);

    DepartmentDTO toDTO(Department department);

    Department toEntity(DepartmentPostDTO newDepartment);

    Department toEntity(DepartmentDTO newDepartment);

}

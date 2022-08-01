package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IDepartmentDAO;
import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;
import com.hahn.banco.entity.Department;
import com.hahn.banco.repository.DepartmentRepository;

@Component
public class DepartmentDAO implements IDepartmentDAO {

    @Autowired
    private DepartmentRepository departmentRepository;
	

    @Override
    public Department create(Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    @Override
    public void delete(Long id) {
        departmentRepository.deleteById(id);   
    }

    @Override
    public List<Department> list(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public Optional<Department> read(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public void update(Department newDepartment, Long id) {
        this.read(id).map(
                department -> {
                    department.setName(newDepartment.getName());
                    departmentRepository.save(department);
                    return true;
                }
        );
    }

    @Override
    public DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName(), department.getState());
    }

    @Override
    public Department toEntity (DepartmentPostDTO departmentDTO) {
        return new Department(departmentDTO.getName());   
    }

    @Override
    public Department toEntity (DepartmentDTO departmentDTO) {
        return new Department(departmentDTO.getId(), departmentDTO.getName());   
    }
    
}

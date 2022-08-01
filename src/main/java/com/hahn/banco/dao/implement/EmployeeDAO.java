package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IEmployeeDAO;
import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.entity.Employee;
import com.hahn.banco.repository.EmployeeRepository;

@Component
public class EmployeeDAO implements IEmployeeDAO {

    @Autowired
    private EmployeeRepository employeeRepository;
	

    @Override
    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);   
    }

    @Override
    public List<Employee> list(String name) {
        //return employeeRepository.fin(name);
        return null;
    }

    @Override
    public Optional<Employee> read(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void update(Employee newEmployee, Long id) {
        this.read(id).map(
                employee -> {
                    employee.setName(newEmployee.getName());
                    employeeRepository.save(employee);
                    return true;
                }
        );
    }

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSurname(), employee.getBirthdate(), employee.getTelephone(), employee.getDocumentType(), employee.getDocument(), null, null, employee.getState());
    }

    @Override
    public Employee toEntity (EmployeePostDTO employeeDTO) {
        return new Employee(null, employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getTelephone(), employeeDTO.getDocumentType(), employeeDTO.getDocument(), employeeDTO.getBirthdate(), null);
    }

    @Override
    public Employee toEntity (EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getId(), null, employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getTelephone(), employeeDTO.getDocumentType(), employeeDTO.getDocument(), employeeDTO.getBirthdate(), null);
    }

}

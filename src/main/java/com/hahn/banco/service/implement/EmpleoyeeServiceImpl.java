package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.entity.Employee;
import com.hahn.banco.repository.EmployeeRepository;
import com.hahn.banco.service.IEmployeeService;


@Service
public class EmpleoyeeServiceImpl implements IEmployeeService {
	
    private static final Log LOGGER = LogFactory.getLog(EmpleoyeeServiceImpl.class);
	
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;
	

	public EmpleoyeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    

    @Override   
    public Optional<EmployeeDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Employee employee = employeeRepository.findById(id).get();
        if(employee.getId() != null) {
            LOGGER.debug("+++ EmpleoyeeServiceImpl:getById: "+employee.toString());
            return Optional.of(this.toDTO(employee));
        }
        LOGGER.debug("--- EmpleoyeeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EmployeeDTO save(EmployeePostDTO newEmployee, Long id_address) {
        // TODO Auto-generated method stub
        Employee employee = this.toEntity(newEmployee);
        LOGGER.debug("+++ EmpleoyeeServiceImpl:save: "+employee.toString());
        return this.toDTO(employeeRepository.save(employee));
    }


    public EmployeeDTO toDTO(Employee employee) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toDTO: "+employee.toString());
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee toEntity (EmployeePostDTO employeeDTO) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+employeeDTO.toString());
        return modelMapper.map(employeeDTO, Employee.class);
    }

    public Employee toEntity (EmployeeDTO employeeDTO) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+employeeDTO.toString());
        return modelMapper.map(employeeDTO, Employee.class);
    }


}

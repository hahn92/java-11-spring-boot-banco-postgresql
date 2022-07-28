package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.entity.Address;
import com.hahn.banco.entity.Employee;
import com.hahn.banco.entity.Role;
import com.hahn.banco.repository.EmployeeRepository;
import com.hahn.banco.service.IEmployeeService;


@Service
public class EmpleoyeeServiceImpl implements IEmployeeService {
	
    private static final Log LOGGER = LogFactory.getLog(EmpleoyeeServiceImpl.class);
	
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Autowired
    private AddressServiceImpl addressServiceImpl;
	

	public EmpleoyeeServiceImpl(EmployeeRepository employeeRepository, RoleServiceImpl roleServiceImpl, AddressServiceImpl addressServiceImpl) {
        this.employeeRepository = employeeRepository;
        this.roleServiceImpl = roleServiceImpl;
        this.addressServiceImpl = addressServiceImpl;
    }
    

    @Override   
    public Optional<EmployeeDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Employee employee = employeeRepository.findById(id).get();
        if(employee.getId() != null) {
            LOGGER.debug("+++ EmpleoyeeServiceImpl:getById: "+employee.toString());
            Long id_role = 1L;
            return Optional.of(this.toDTO(employee, id_role, employee.getAddress().getId()));
        }
        LOGGER.debug("--- EmpleoyeeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EmployeeDTO save(EmployeePostDTO newEmployee, Long id_address) {
        // TODO Auto-generated method stub
        Long id_role = 1L;
        Employee employee = this.toEntity(newEmployee, id_role, id_address);
        LOGGER.debug("+++ EmpleoyeeServiceImpl:save: "+employee.toString());
        return this.toDTO(employeeRepository.save(employee), id_role, id_address);
    }


    public EmployeeDTO toDTO(Employee employee, Long id_role, Long id_address) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toDTO: "+employee.toString());
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getSurname(), employee.getBirthdate(), employee.getTelephone(), employee.getDocumentType(), employee.getDocument(), addressServiceImpl.getById(id_address).get(), roleServiceImpl.getById(id_role).get(), employee.getState());
    }

    public Employee toEntity (EmployeePostDTO employeeDTO, Long id_role, Long id_address) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+employeeDTO.toString());
        Role role = roleServiceImpl.toEntity(roleServiceImpl.getById(id_role).get());
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+role.toString());
        AddressDTO addressDTO = addressServiceImpl.getById(id_address).get();
        Address address = addressServiceImpl.toEntity(addressDTO, addressDTO.getCity().getId());
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+role.toString());
        return new Employee(address, employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getTelephone(), employeeDTO.getDocumentType(), employeeDTO.getDocument(), employeeDTO.getBirthdate(), role);   
    }

    public Employee toEntity (EmployeeDTO employeeDTO, Long id_role, Long id_address) {
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+employeeDTO.toString());
        Role role = roleServiceImpl.toEntity(roleServiceImpl.getById(id_role).get());
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+role.toString());
        AddressDTO addressDTO = addressServiceImpl.getById(id_address).get();
        Address address = addressServiceImpl.toEntity(addressDTO, addressDTO.getCity().getId());
        LOGGER.debug("+++ EmpleoyeeServiceImpl:toEntity: "+role.toString());
        return new Employee(employeeDTO.getId(), address, employeeDTO.getName(), employeeDTO.getSurname(), employeeDTO.getTelephone(), employeeDTO.getDocumentType(), employeeDTO.getDocument(), employeeDTO.getBirthdate(), role);   
    }


}

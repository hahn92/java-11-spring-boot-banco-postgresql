package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IAddressDAO;
import com.hahn.banco.dao.IEmployeeDAO;
import com.hahn.banco.dao.IRoleDAO;
import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.dto.employee.EmployeePostDTO;
import com.hahn.banco.entity.Employee;
import com.hahn.banco.service.IEmployeeService;


@Service
public class EmpleoyeeServiceImpl implements IEmployeeService {
	
    private static final Log LOGGER = LogFactory.getLog(EmpleoyeeServiceImpl.class);
	
    @Autowired
    private IEmployeeDAO iEmployeeDAO;
    @Autowired
    private IAddressDAO iAddressDAO;
    @Autowired
    private IRoleDAO iRoleDAO;
	

	public EmpleoyeeServiceImpl(IEmployeeDAO iEmployeeDAO, IAddressDAO iAddressDAO, IRoleDAO iRoleDAO) {
        this.iEmployeeDAO = iEmployeeDAO;
        this.iAddressDAO = iAddressDAO;
        this.iRoleDAO = iRoleDAO;
    }
    

    @Override   
    public Optional<EmployeeDTO> getById(Long id) {
        Employee employee = iEmployeeDAO.read(id).get();
        if(employee.getId() != null) {
            LOGGER.debug("+++ EmpleoyeeServiceImpl:getById: "+employee.toString());
            EmployeeDTO employeeDTO = iEmployeeDAO.toDTO(employee);
            iAddressDAO.read(employee.getAddress().getId()).ifPresent(address -> employeeDTO.setAddress(iAddressDAO.toDTO(address)));
            iRoleDAO.read(employee.getRole().getId()).ifPresent(role -> employeeDTO.setRole(iRoleDAO.toDTO(role)));
            return Optional.of(employeeDTO);
        }
        LOGGER.debug("--- EmpleoyeeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public EmployeeDTO save(EmployeePostDTO newEmployee, Long id_address, Long id_role) {
        Employee employee = iEmployeeDAO.toEntity(newEmployee);
        LOGGER.debug("+++ EmpleoyeeServiceImpl:save: "+employee.toString());
        iAddressDAO.read(id_address).ifPresent(address -> employee.setAddress(address));
        iRoleDAO.read(id_role).ifPresent(role -> employee.setRole(role));
        return iEmployeeDAO.toDTO(iEmployeeDAO.create(employee));
    }

}

package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.entity.BranchOffice;
import com.hahn.banco.entity.Employee;
import com.hahn.banco.repository.BranchOfficeRepository;
import com.hahn.banco.service.IBranchOfficeService;


@Service
public class BranchOfficeServiceImpl implements IBranchOfficeService{
	
    private static final Log LOGGER = LogFactory.getLog(BranchOfficeServiceImpl.class);
	
    @Autowired
    private BranchOfficeRepository branchOfficeRepository;
    @Autowired
    private AddressServiceImpl addressServiceImpl;
    @Autowired
    private EmpleoyeeServiceImpl empleoyeeServiceImpl;
	
    public BranchOfficeServiceImpl(BranchOfficeRepository branchOfficeRepository, AddressServiceImpl addressServiceImpl, EmpleoyeeServiceImpl empleoyeeServiceImpl) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.addressServiceImpl = addressServiceImpl;
        this.empleoyeeServiceImpl = empleoyeeServiceImpl;
    }

    @Override   
    public Optional<BranchOfficeDTO> getById(Long id) {
        // TODO Auto-generated method stub
        BranchOffice branchOffice = branchOfficeRepository.findById(id).get();
        if(branchOffice.getId() != null) {
            LOGGER.debug("+++ BranchOfficeServiceImpl:getById: "+branchOffice.toString());
            return Optional.of(this.toDTO(branchOffice, branchOffice.getEmployee().getId()));
        }
        LOGGER.debug("--- BranchOfficeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BranchOfficeDTO save(BranchOfficePostDTO newBranchOffice, Long id_employee) {
        // TODO Auto-generated method stub
        BranchOffice branchOffice = this.toEntity(newBranchOffice, id_employee);
        LOGGER.debug("+++ BranchOfficeServiceImpl:save: "+branchOffice.toString());
        return this.toDTO(branchOfficeRepository.save(branchOffice), id_employee);
    }


    public BranchOfficeDTO toDTO(BranchOffice branchOffice, Long id_employee) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toDTO: "+branchOffice.toString());
        return new BranchOfficeDTO(branchOffice.getId(), branchOffice.getName(), branchOffice.getCode(), addressServiceImpl.toDTO(branchOffice.getAddress(), branchOffice.getAddress().getCity().getId()), empleoyeeServiceImpl.toDTO(branchOffice.getEmployee(), branchOffice.getEmployee().getRole().getId(), branchOffice.getEmployee().getAddress().getId()), branchOffice.getState());
    }

    public BranchOffice toEntity (BranchOfficePostDTO branchOfficeDTO, Long id_employee) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+branchOfficeDTO.toString());
        EmployeeDTO employeeDTO = empleoyeeServiceImpl.getById(id_employee).get();
        Employee employee = empleoyeeServiceImpl.toEntity(employeeDTO, employeeDTO.getRole().getId(), employeeDTO.getAddress().getId());
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+employee.toString());
        return new BranchOffice(employee, employee.getAddress(), branchOfficeDTO.getName(), branchOfficeDTO.getCode());   
    }

    public BranchOffice toEntity (BranchOfficeDTO branchOfficeDTO, Long id_employee) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+branchOfficeDTO.toString());
        EmployeeDTO employeeDTO = empleoyeeServiceImpl.getById(id_employee).get();
        Employee employee = empleoyeeServiceImpl.toEntity(employeeDTO, employeeDTO.getRole().getId(), employeeDTO.getAddress().getId());
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+employee.toString());
        return new BranchOffice(branchOfficeDTO.getId(), employee, employee.getAddress(), branchOfficeDTO.getName(), branchOfficeDTO.getCode());   
    }

}

package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IAddressDAO;
import com.hahn.banco.dao.IBranchOfficeDAO;
import com.hahn.banco.dao.IEmployeeDAO;
import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.entity.BranchOffice;
import com.hahn.banco.service.IBranchOfficeService;


@Service
public class BranchOfficeServiceImpl implements IBranchOfficeService{
	
    private static final Log LOGGER = LogFactory.getLog(BranchOfficeServiceImpl.class);
	
    @Autowired
    private IBranchOfficeDAO iBranchOfficeDAO;
    @Autowired
    private IEmployeeDAO iEmployeeDAO;
    @Autowired
    private IAddressDAO iAddressDAO;
	

    public BranchOfficeServiceImpl(IBranchOfficeDAO iBranchOfficeDAO, IEmployeeDAO iEmployeeDAO, IAddressDAO iAddressDAO) {
        this.iBranchOfficeDAO = iBranchOfficeDAO;
        this.iEmployeeDAO = iEmployeeDAO;
        this.iAddressDAO = iAddressDAO;
    }

    
    @Override   
    public Optional<BranchOfficeDTO> getById(Long id) {
        BranchOffice branchOffice = iBranchOfficeDAO.read(id).get();
        if(branchOffice.getId() != null) {
            LOGGER.debug("+++ BranchOfficeServiceImpl:getById: "+branchOffice.toString());
            BranchOfficeDTO branchOfficeDTO = iBranchOfficeDAO.toDTO(branchOffice);
            iEmployeeDAO.read(branchOffice.getEmployee().getId()).ifPresent(department -> branchOfficeDTO.setEmployee(iEmployeeDAO.toDTO(department)));
            return Optional.of(branchOfficeDTO);
        }
        LOGGER.debug("--- BranchOfficeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BranchOfficeDTO save(BranchOfficePostDTO newBranchOffice, Long id_address, Long id_employee) {
        BranchOffice branchOffice = iBranchOfficeDAO.toEntity(newBranchOffice);
        LOGGER.debug("+++ BranchOfficeServiceImpl:save: "+branchOffice.toString());
        iEmployeeDAO.read(id_employee).ifPresent(employee -> branchOffice.setEmployee(employee));
        iAddressDAO.read(id_address).ifPresent(address -> branchOffice.setAddress(address));
        return iBranchOfficeDAO.toDTO(iBranchOfficeDAO.create(branchOffice));
    }


}

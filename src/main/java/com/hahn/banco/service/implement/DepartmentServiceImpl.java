package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dao.IDepartmentDAO;
import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;
import com.hahn.banco.entity.Department;
import com.hahn.banco.service.IDepartmentService;


@Service
public class DepartmentServiceImpl implements IDepartmentService {
	
    private static final Log LOGGER = LogFactory.getLog(DepartmentServiceImpl.class);
	
    @Autowired
    private IDepartmentDAO iDepartmentDAO;
	

	public DepartmentServiceImpl(IDepartmentDAO iDepartmentDAO) {
        this.iDepartmentDAO = iDepartmentDAO;
    }


    @Override   
    public Optional<DepartmentDTO> getById(Long id) {
        LOGGER.debug("+++ DepartmentServiceImpl:getById: "+id);
        Department department = iDepartmentDAO.read(id).get();
        if(department.getId() != null) {
            return Optional.of(iDepartmentDAO.toDTO(department));
        }
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public DepartmentDTO save(DepartmentPostDTO newDepartment) {
        LOGGER.debug("+++ DepartmentServiceImpl:save: "+newDepartment);
        Department department = iDepartmentDAO.toEntity(newDepartment); 
        return iDepartmentDAO.toDTO(iDepartmentDAO.create(department));
    }

}

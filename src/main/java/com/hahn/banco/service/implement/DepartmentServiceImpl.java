package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.dto.department.DepartmentPostDTO;
import com.hahn.banco.entity.Department;
import com.hahn.banco.repository.DepartmentRepository;
import com.hahn.banco.service.IDepartmentService;


@Service
public class DepartmentServiceImpl implements IDepartmentService{
	
    private static final Log LOGGER = LogFactory.getLog(DepartmentServiceImpl.class);
	
    @Autowired
    private DepartmentRepository departmentRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override   
    public Optional<DepartmentDTO> getById(Long id) {
        // TODO Auto-generated method stub
        Department department = departmentRepository.findById(id).get();
        if(department.getId() == null) {
            LOGGER.debug("+++ getById: "+department.toString());
            return Optional.of(this.toDTO(department));
        }
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public DepartmentDTO save(DepartmentPostDTO newDepartment) {
        // TODO Auto-generated method stub
        Department department = this.toEntity(newDepartment); 
        LOGGER.debug("+++ save: "+department.toString());
        return this.toDTO(departmentRepository.save(department));
    }


    private DepartmentDTO toDTO(Department department) {
        LOGGER.debug("+++ toDTO: "+department.toString());
        return new DepartmentDTO(department.getName(), department.getState());
    }

    private Department toEntity (DepartmentPostDTO departmentDTO) {
        LOGGER.debug("+++ toEntity: "+departmentDTO.toString());
        return new Department(departmentDTO.getName());   
    }

}

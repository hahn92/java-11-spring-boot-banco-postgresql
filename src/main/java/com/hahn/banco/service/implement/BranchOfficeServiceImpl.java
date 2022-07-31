package com.hahn.banco.service.implement;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.entity.BranchOffice;
import com.hahn.banco.repository.BranchOfficeRepository;
import com.hahn.banco.service.IBranchOfficeService;


@Service
public class BranchOfficeServiceImpl implements IBranchOfficeService{
	
    private static final Log LOGGER = LogFactory.getLog(BranchOfficeServiceImpl.class);
	
    @Autowired
    private BranchOfficeRepository branchOfficeRepository;
    @Autowired
    private ModelMapper modelMapper;


    public BranchOfficeServiceImpl(BranchOfficeRepository branchOfficeRepository, ModelMapper modelMapper) {
        this.branchOfficeRepository = branchOfficeRepository;
        this.modelMapper = modelMapper;
    }
    

    @Override   
    public Optional<BranchOfficeDTO> getById(Long id) {
        // TODO Auto-generated method stub
        BranchOffice branchOffice = branchOfficeRepository.findById(id).get();
        if(branchOffice.getId() != null) {
            LOGGER.debug("+++ BranchOfficeServiceImpl:getById: "+branchOffice.toString());
            return Optional.of(this.toDTO(branchOffice));
        }
        LOGGER.debug("--- BranchOfficeServiceImpl:getById: No existe la ciudad con id: "+id);
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BranchOfficeDTO save(BranchOfficePostDTO newBranchOffice, Long id_address, Long id_employee) {
        // TODO Auto-generated method stub
        BranchOffice branchOffice = this.toEntity(newBranchOffice);
        LOGGER.debug("+++ BranchOfficeServiceImpl:save: "+branchOffice.toString());
        return this.toDTO(branchOfficeRepository.save(branchOffice));
    }

    
    public BranchOfficeDTO toDTO(BranchOffice branchOffice) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toDTO: "+branchOffice.toString());
        return modelMapper.map(branchOffice, BranchOfficeDTO.class);
    }

    public BranchOffice toEntity (BranchOfficePostDTO branchOfficeDTO) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+branchOfficeDTO.toString());
        return modelMapper.map(branchOfficeDTO, BranchOffice.class);
    }

    public BranchOffice toEntity (BranchOfficeDTO branchOfficeDTO) {
        LOGGER.debug("+++ BranchOfficeServiceImpl:toEntity: "+branchOfficeDTO.toString());
        return modelMapper.map(branchOfficeDTO, BranchOffice.class); 
    }

}

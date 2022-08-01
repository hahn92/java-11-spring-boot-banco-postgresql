package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IBranchOfficeDAO;
import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.entity.BranchOffice;
import com.hahn.banco.repository.BranchOfficeRepository;

@Component
public class BranchOfficeDAO implements IBranchOfficeDAO {

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;
	

    @Override
    public BranchOffice create(BranchOffice newBranchOffice) {
        return branchOfficeRepository.save(newBranchOffice);
    }

    @Override
    public void delete(Long id) {
        branchOfficeRepository.deleteById(id);   
    }

    @Override
    public List<BranchOffice> list(String name) {
        return branchOfficeRepository.findByName(name);
    }

    @Override
    public Optional<BranchOffice> read(Long id) {
        return branchOfficeRepository.findById(id);
    }

    @Override
    public void update(BranchOffice newBranchOffice, Long id) {
        this.read(id).map(
                branchOffice -> {
                    branchOffice.setName(newBranchOffice.getName());
                    branchOfficeRepository.save(branchOffice);
                    return true;
                }
        );
    }

    @Override
    public BranchOfficeDTO toDTO(BranchOffice branchOffice) {
        return new BranchOfficeDTO(branchOffice.getId(), branchOffice.getName(), branchOffice.getCode(), null, null,  branchOffice.getState());
    }

    @Override
    public BranchOffice toEntity (BranchOfficePostDTO branchOfficeDTO) {
        return new BranchOffice(null, null, branchOfficeDTO.getName(), branchOfficeDTO.getCode());  
    }

    @Override
    public BranchOffice toEntity (BranchOfficeDTO branchOfficeDTO) {
        return new BranchOffice(branchOfficeDTO.getId(), null, null, branchOfficeDTO.getName(), branchOfficeDTO.getCode());  
    }

    
}

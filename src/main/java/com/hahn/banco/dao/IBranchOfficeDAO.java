package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;
import com.hahn.banco.entity.BranchOffice;

public interface IBranchOfficeDAO {

    Optional<BranchOffice> read(Long id);

    List<BranchOffice> list(String name);

    BranchOffice create(BranchOffice newBranchOffice);
    
    void update(BranchOffice newBranchOffice, Long id);
    
    void delete(Long id);

    BranchOfficeDTO toDTO(BranchOffice branchOffice);

    BranchOffice toEntity(BranchOfficePostDTO newBranchOffice);

    BranchOffice toEntity(BranchOfficeDTO newBranchOffice);

}

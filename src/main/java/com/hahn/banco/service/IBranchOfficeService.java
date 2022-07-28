package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.branchOffice.BranchOfficePostDTO;

public interface IBranchOfficeService {

    Optional<BranchOfficeDTO> getById(Long id);

    BranchOfficeDTO save(BranchOfficePostDTO newBranchOffice, Long id_employee);
    
}

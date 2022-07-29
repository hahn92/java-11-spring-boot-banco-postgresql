package com.hahn.banco.service;

import java.util.Optional;

import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;

public interface IOperationService {

    Optional<OperationDTO> getById(Long id);

    OperationDTO save(OperationDTO newOperation);

    Boolean operation(OperationPostDTO newOperation, Long id_origin, Long id_destiny);
    
}

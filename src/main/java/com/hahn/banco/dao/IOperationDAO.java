package com.hahn.banco.dao;

import java.util.List;
import java.util.Optional;

import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;
import com.hahn.banco.entity.Operation;

public interface IOperationDAO {

    Optional<Operation> read(Long id);

    List<Operation> list(String name);

    List<Operation> readTopFive(Long id);

    Operation create(Operation newOperation);
    
    void update(Operation newOperation, Long id);
    
    void delete(Long id);

    OperationDTO toDTO(Operation operation);

    Operation toEntity(OperationPostDTO newOperation);

    Operation toEntity(OperationDTO newOperation);

}

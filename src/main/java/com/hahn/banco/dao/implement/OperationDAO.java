package com.hahn.banco.dao.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hahn.banco.dao.IOperationDAO;
import com.hahn.banco.dto.operation.OperationDTO;
import com.hahn.banco.dto.operation.OperationPostDTO;
import com.hahn.banco.entity.Operation;
import com.hahn.banco.repository.OperationRepository;

@Component
public class OperationDAO implements IOperationDAO {

    @Autowired
    private OperationRepository operationRepository;
	

    @Override
    public Operation create(Operation newOperation) {
        return operationRepository.save(newOperation);
    }

    @Override
    public void delete(Long id) {
        operationRepository.deleteById(id);   
    }

    @Override
    public List<Operation> list(String name) {
        //return operationRepository.findByName(name);
        return null;
    }

    @Override
    public List<Operation> readTopFive(Long id) {
        return operationRepository.findTop5ByAccountId(id);
    }

    @Override
    public Optional<Operation> read(Long id) {
        return operationRepository.findById(id);
    }

    @Override
    public void update(Operation newOperation, Long id) {
        this.read(id).map(
                operation -> {
                    operation.setAmount(newOperation.getAmount());
                    operationRepository.save(operation);
                    return true;
                }
        );
    }

    @Override
    public OperationDTO toDTO(Operation operation) {
        return new OperationDTO(operation.getId(), null, null, operation.getOperationType(), operation.getBalance(), operation.getAmount(), operation.getDescription(), operation.getState());
    }

    @Override
    public Operation toEntity (OperationPostDTO operationDTO) {
        return new Operation(null, null, operationDTO.getOperationType(), operationDTO.getBalance(), operationDTO.getAmount(), operationDTO.getDescription());  
    }

    @Override
    public Operation toEntity (OperationDTO operationDTO) {
        return new Operation(operationDTO.getId(), null, null, operationDTO.getOperationType(), operationDTO.getBalance(), operationDTO.getAmount(), operationDTO.getDescription());    
    }

    
}

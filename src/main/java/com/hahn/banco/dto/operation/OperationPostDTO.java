package com.hahn.banco.dto.operation;

import com.hahn.banco.entity.constant.OperationType;

public class OperationPostDTO {

	private OperationType operationType;
	private Double balance;
	private Double amount;
	private String description;
	
	
	public OperationPostDTO(OperationType operationType, Double balance, Double amount, String description) {
		this.operationType = operationType;
		this.balance = balance;
		this.amount = amount;
		this.description = description;
	}


	public OperationType getOperationType() {
		return operationType;
	}
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public String toString() {
		return "OperationPostDTO{ operationType=" + operationType + ", balance=" + balance + ", amount=" + amount + ", description=" + description + "}";
	}

}

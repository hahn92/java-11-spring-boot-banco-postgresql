package com.hahn.banco.dto.operation;

import com.hahn.banco.entity.constant.OperationType;

public class OperationPostDTO {

	private Long account;
	private Long transaction;
	private OperationType operationType;
	private Double balance;
	private Double amount;
	private String description;
	
	
	public OperationPostDTO(Long account, Long transaction, OperationType operationType, Double balance,
		Double amount, String description) {
		this.account = account;
		this.transaction = transaction;
		this.operationType = operationType;
		this.balance = balance;
		this.amount = amount;
		this.description = description;
	}


	public Long getAccount() {
		return account;
	}


	public void setAccount(Long account) {
		this.account = account;
	}


	public Long getTransaction() {
		return transaction;
	}


	public void setTransaction(Long transaction) {
		this.transaction = transaction;
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

}

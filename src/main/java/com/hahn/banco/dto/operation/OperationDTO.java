package com.hahn.banco.dto.operation;

import com.hahn.banco.dto.account.AccountDTO;
import com.hahn.banco.dto.transaction.TransactionDTO;
import com.hahn.banco.entity.constant.OperationType;
import com.hahn.banco.entity.constant.StateType;

public class OperationDTO {

	private long id;
	private AccountDTO account;
	private TransactionDTO transaction;
	private OperationType operationType;
	private Double balance;
	private Double amount;
	private String description;
	private StateType state;

	
	public OperationDTO() {
		super();
	}
	public OperationDTO(Long id, AccountDTO account, TransactionDTO transaction, OperationType operationType, Double balance,
		Double amount, String description, StateType state) {
		this.id = id;
		this.account = account;
		this.transaction = transaction;
		this.operationType = operationType;
		this.balance = balance;
		this.amount = amount;
		this.description = description;
		this.state = state;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public AccountDTO getAccount() {
		return account;
	}


	public void setAccount(AccountDTO account) {
		this.account = account;
	}


	public TransactionDTO getTransaction() {
		return transaction;
	}


	public void setTransaction(TransactionDTO transaction) {
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


	public StateType getState() {
		return state;
	}


	public void setState(StateType state) {
		this.state = state;
	}

	
	@Override
	public String toString() {
		return "OperationDTO [id=" + id + ", account=" + account + ", transaction=" + transaction + ", operationType="
				+ operationType + ", balance=" + balance + ", amount=" + amount + ", description=" + description
				+ ", state=" + state + "]";
	}

}

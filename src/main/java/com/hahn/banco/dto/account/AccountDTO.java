package com.hahn.banco.dto.account;

import com.hahn.banco.dto.branchOffice.BranchOfficeDTO;
import com.hahn.banco.dto.client.ClientDTO;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.entity.constant.StateType;

public class AccountDTO {

	private long id;
	private BranchOfficeDTO branchOffice;
	private ClientDTO client;
	private String accountNumber;
	private Double balance;
	private Double beginBalance;
	private AccountType accountType;
	private StateType state;

	
	public AccountDTO() {
		super();
	}
    public AccountDTO(Long id, BranchOfficeDTO branchOffice, ClientDTO client, String accountNumber,  Double balance, 
            Double beginBalance, AccountType accountType, StateType state) {
		this.id = id;
        this.branchOffice = branchOffice;
        this.client = client;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.beginBalance = beginBalance;
        this.accountType = accountType;
		this.state = state;
    }


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BranchOfficeDTO getBranchOffice() {
		return branchOffice;
	}
	public void setBranchOffice(BranchOfficeDTO branchOffice) {
		this.branchOffice = branchOffice;
	}
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getBeginBalance() {
		return beginBalance;
	}
	public void setBeginBalance(Double beginBalance) {
		this.beginBalance = beginBalance;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", branchOffice=" + branchOffice + ", client=" + client + ", accountNumber="
				+ accountNumber + ", balance=" + balance + ", beginBalance=" + beginBalance + ", accountType="
				+ accountType + ", state=" + state + "]";
	}

}

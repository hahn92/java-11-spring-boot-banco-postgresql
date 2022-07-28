package com.hahn.banco.dto.account;

import com.hahn.banco.entity.constant.AccountType;

public class AccountPostDTO {

	private String accountNumber;
	private Double balance;
	private Double beginBalance;
	private AccountType accountType;

	
	public AccountPostDTO(String accountNumber,  Double balance, Double beginBalance, AccountType accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.beginBalance = beginBalance;
        this.accountType = accountType;
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

}

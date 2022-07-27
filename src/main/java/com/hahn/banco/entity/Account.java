package com.hahn.banco.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hahn.banco.entity.constant.AccountType;
import com.hahn.banco.entity.generic.Audit;

@Entity
public class Account extends Audit{
	
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_branchOffice", nullable=false)
    private BranchOffice branchOffice;
	
    @ManyToOne
    @JoinColumn(name = "id_client", nullable=false)
    @JsonBackReference
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Operation> operation = new ArrayList<>();

    @Column(name="accountNumber", nullable=false, unique = true, length=20)
    private String accountNumber;

    @Column(name="balance", nullable=false, length=20)
    private Double balance;

    @Column(name="beginBalance", nullable=false, length=20)
    private Double beginBalance;

    @Enumerated(EnumType.STRING)
    @Column(name="accountType", nullable=false, length=10)
    private AccountType accountType;


    public Account() {
        super();
    }
    public Account(BranchOffice branchOffice, Client client, String accountNumber,  Double balance, 
            Double beginBalance, AccountType accountType) {
        super();
        this.branchOffice = branchOffice;
        this.client = client;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.beginBalance = beginBalance;
        this.accountType = accountType;
    }


    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Client getClient() {
        return client;
    }

    public List<Operation> getOperation() {
        return operation;
    }

    public void setOperation(List<Operation> operation) {
        this.operation = operation;
    }

    public String getAccountNumber() {
        return accountNumber;
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

    @Override
    public String toString() {
        return "Account[id=" + getId() + ", accountNumber=" + accountNumber + ", balance=" + balance 
        + ", beginBalance=" + beginBalance + ", accountType=" + accountType + ", state = " + getState() + "]";
    }
    
}

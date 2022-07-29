package com.hahn.banco.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hahn.banco.entity.constant.OperationType;
import com.hahn.banco.entity.generic.Audit;

@Entity
public class Operation extends Audit{
	
    @ManyToOne
    @JoinColumn(name = "id_account", nullable=false)
    @JsonBackReference
    private Account account;
	
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_transaction", nullable=false)
    private Transaction transaction;
	
    @Enumerated(EnumType.STRING)
	@Column(name="operationType", nullable=false, length=20)
	private OperationType operationType;
	
    @Column(name="balance", nullable=false, length=20)
    private Double balance;

    @Column(name="amount", nullable=false, length=20)
    private Double amount;

    @Column(name="description", nullable=false, length=140)
    private String description;


    public Operation() {
        super();
    }
    public Operation(Account account, Transaction transaction, OperationType operationType, Double balance,
            Double amount, String description) {
        super();
        this.account = account;
        this.transaction = transaction;
        this.operationType = operationType;
        this.balance = balance;
        this.amount = amount;
        this.description = description;
    }
    public Operation(Long id, Account account, Transaction transaction, OperationType operationType, Double balance,
            Double amount, String description) {
        super(id);
        this.account = account;
        this.transaction = transaction;
        this.operationType = operationType;
        this.balance = balance;
        this.amount = amount;
        this.description = description;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
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

    
    @Override
    public String toString() {
        return "Operation [id=" + getId() + ", account=" + account + ", transaction=" + transaction + ", operationType="
        + operationType + ", balance=" + balance + ", amount=" + amount + ", description=" + description + ", state=" + getState() + "]";
    }

}

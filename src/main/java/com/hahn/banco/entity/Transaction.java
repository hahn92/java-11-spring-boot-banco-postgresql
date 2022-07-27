package com.hahn.banco.entity;

import javax.persistence.Entity;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class Transaction extends Audit{

    public Transaction() {
        super();
    }

    @Override
    public String toString() {
        return "Transaction [id=" + getId() + ", state=" + getState() + "]";
    }

}

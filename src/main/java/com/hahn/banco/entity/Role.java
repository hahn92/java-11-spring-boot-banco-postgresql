package com.hahn.banco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class Role extends Audit{

    @Column(name="name", nullable=false, unique=true, length=20)
    private String name;

    
    public Role() {
        super();
    }
    public Role(String name) {
        super();
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Role [id=" + getId() + ", name=" + name + ", state = " + getState() + "]";
    }

}

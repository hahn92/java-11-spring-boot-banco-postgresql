package com.hahn.banco.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class Department extends Audit{
	
    @Column(name="name", nullable=false, length=20)
    private String name;

    
    public Department() {
        super();
    }
    public Department(String name) {
        super();
        this.name = name;
    }
    public Department(Long id, String name) {
        super(id);
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
        return "Department [id=" + getId() + ", name=" + name + ", state=" + getState() + "]";
    }

}

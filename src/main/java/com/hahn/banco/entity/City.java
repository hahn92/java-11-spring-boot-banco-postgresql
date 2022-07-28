package com.hahn.banco.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class City extends Audit{
	
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_department", nullable=false)
    private Department department;

    @Column(name="name", nullable=false, length=20)
    private String name;


    public City() {
        super();
    }
    public City(Department department, String name) {
        super();
        this.department = department;
        this.name = name;
    }
    public City(Long id, Department department, String name) {
        super(id);
        this.department = department;
        this.name = name;
    }

    
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    @Override
    public String toString() {
        return "City [id=" + getId() + ", department=" + department + ", name=" + name + ", state=" + getState() + "]";
    }

}

package com.hahn.banco.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class BranchOffice extends Audit{
	
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_employee", nullable=false)
    private Employee employee;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_address", nullable=false)
    private Address address;

    @Column(name="name", nullable=false, length=20)
    private String name;

    @Column(name="code", nullable=false, unique = true, length=20)
    private String code;
    

    public BranchOffice() {
        super();
    }
    public BranchOffice(Employee employee, Address address, String name, String code) {
        super();
        this.employee = employee;
        this.address = address;
        this.name = name;
        this.code = code;
    }
    public BranchOffice(Long id, Employee employee, Address address, String name, String code) {
        super(id);
        this.employee = employee;
        this.address = address;
        this.name = name;
        this.code = code;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "BranchOffice [id=" + getId() + ", name=" + name + ", code = " + code + ", state=" + getState() + "]";
    }

}

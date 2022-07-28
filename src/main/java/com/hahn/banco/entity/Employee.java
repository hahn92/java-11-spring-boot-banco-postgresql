package com.hahn.banco.entity;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hahn.banco.entity.constant.DocumentType;
import com.hahn.banco.entity.generic.Person;

@Entity
public class Employee extends Person{
    
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_role", nullable=false)
    private Role role;

    
    public Employee() {
        super();
    }
    public Employee(Address address, String name, String surname, String telephone, DocumentType documentType, String document, Date birthdate) {
		super(address, name, surname, telephone, documentType, document, birthdate);
    }
    public Employee(Address address, String name, String surname, String telephone, DocumentType documentType, 
            String document, Date birthdate, Role role) {
        super(address, name, surname, telephone, documentType, document, birthdate);
        this.role = role;
    }
    public Employee(Long id, Address address, String name, String surname, String telephone, DocumentType documentType, 
            String document, Date birthdate, Role role) {
        super(id, address, name, surname, telephone, documentType, document, birthdate);
        this.role = role;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    

    @Override
    public String toString() {
        return "Employee [id=" + getId() + ", name=" + getName() + ", surname=" + getSurname() + ", telephone="
                + getTelephone() + ", documentType=" + getDocumentType() + ", document=" + getDocument()
                + ", birthdate=" + getBirthdate() + ", role=" + role + ", state=" + getState() + "]";
    }

}

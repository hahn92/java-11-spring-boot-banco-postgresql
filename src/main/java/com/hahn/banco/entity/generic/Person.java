package com.hahn.banco.entity.generic;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.hahn.banco.entity.Address;
import com.hahn.banco.entity.constant.DocumentType;


@MappedSuperclass
public class Person extends Audit {

	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_address", nullable=false)
    private Address address;

	@Column(name="name", nullable=false, length=20)
	private String name;

	@Column(name="surname", nullable=false, length=20)
	private String surname;
		 
	@Column(name="telephone", length=20)
	private String telephone;
		 
	@Enumerated(EnumType.STRING)
	@Column(name="documentType", length=2)
	private DocumentType documentType;
		 
	@Column(name="document", length=20)
	private String document;

	@Column(name="birthdate", nullable = false)
	private Date birthdate;

	@Column(name="highDate", nullable=false)
	private Date highDate;

	@Column(name="downDate", nullable=true)
	private Date downDate;

	
	public Person() {
		super();
	}
	public Person(Address address, String name, String surname, String telephone, DocumentType documentType, String document, Date birthdate) {
       	super();
		this.address = address;
		this.name = name;
		this.surname = surname;
		this.telephone = telephone;
		this.documentType = documentType;
		this.document = document;
		this.birthdate = birthdate;
		Date dateNow = new Date(System.currentTimeMillis());
		this.highDate = dateNow;
	}
	public Person(Long id, Address address, String name, String surname, String telephone, DocumentType documentType, String document, Date birthdate) {
       	super(id);
		this.address = address;
		this.name = name;
		this.surname = surname;
		this.telephone = telephone;
		this.documentType = documentType;
		this.document = document;
		this.birthdate = birthdate;
		Date dateNow = new Date(System.currentTimeMillis());
		this.highDate = dateNow;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
        Date dateNow = Date.valueOf(birthdate);
		this.birthdate = dateNow;
	}
	
	public Date getHighDate() {
		return highDate;
	}

	public Date getDownDate() {
		return downDate;
	}

	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}

    @Override
	public String toString() {
		return "Person [name=" + name + ", surname=" + surname + ", telephone=" + telephone + ", documentType=" + documentType + ", document=" + document + ", birthdate=" + birthdate + ", highDate=" + highDate + ", downDate=" + downDate + ", state = " + getState() + "]";
	}

}

package com.hahn.banco.dto.employee;

import java.sql.Date;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.entity.constant.DocumentType;
import com.hahn.banco.entity.constant.StateType;

public class EmployeeDTO {

	private long id;
	private String name;
	private String surname;
	private Date birthdate;
	private String telephone;
	private DocumentType documentType;
	private String document;
	private AddressDTO address;
	private RoleDTO role;
	private StateType state;


	public EmployeeDTO(long id, String name, String surname, Date birthdate, String telephone,
			DocumentType documentType, String document, AddressDTO address, RoleDTO role, StateType state) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.telephone = telephone;
		this.documentType = documentType;
		this.document = document;
		this.address = address;
		this.role = role;
		this.state = state;
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
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
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}
	
}

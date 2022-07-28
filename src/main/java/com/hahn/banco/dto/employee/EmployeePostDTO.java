package com.hahn.banco.dto.employee;

import java.sql.Date;

import com.hahn.banco.dto.role.RoleDTO;
import com.hahn.banco.entity.constant.DocumentType;

public class EmployeePostDTO {

	private String name;
	private String surname;
	private DocumentType documentType;
	private String document;
	private String telephone;
	private Date birthdate;
	private Long address;
	private Long role;


	public EmployeePostDTO(String name, String surname, DocumentType documentType, String document, String telephone,
			Date birthdate, Long address, Long role) {
		this.name = name;
		this.surname = surname;
		this.documentType = documentType;
		this.document = document;
		this.telephone = telephone;
		this.birthdate = birthdate;
		this.address = address;
		this.role = role;
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public Long getAddress() {
		return address;
	}
	public void setAddress(Long address) {
		this.address = address;
	}
	public Long getRole() {
		return role;
	}
	public void setRole(Long role) {
		this.role = role;
	}

}
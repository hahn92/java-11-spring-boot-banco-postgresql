package com.hahn.banco.dto.employee;

import java.sql.Date;

import com.hahn.banco.entity.constant.DocumentType;

public class EmployeePostDTO {

	private String name;
	private String surname;
	private DocumentType documentType;
	private String document;
	private String telephone;
	private Date birthdate;


	public EmployeePostDTO() {
		super();
	}
	public EmployeePostDTO(String name, String surname, DocumentType documentType, String document, String telephone,
			Date birthdate) {
		this.name = name;
		this.surname = surname;
		this.documentType = documentType;
		this.document = document;
		this.telephone = telephone;
		this.birthdate = birthdate;
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

	@Override
    public String toString() {
		return "EmployeePostDTO{ name=" + name + ", surname=" + surname + ", documentType=" + documentType
				+ ", document=" + document + ", telephone=" + telephone + ", birthdate=" + birthdate + "}";
	}

}
package com.hahn.banco.dto.client;

import java.sql.Date;

import com.hahn.banco.entity.constant.DocumentType;

public class ClientPostDTO {

	private String username;
	private String password;
	private String email;
	private String name;
	private String surname;
	private DocumentType documentType;
	private String document;
	private String telephone;
	private Date birthdate;

	
	public ClientPostDTO() {
	}
	public ClientPostDTO(String username, String password, String email, String name, String surname,
			DocumentType documentType, String document, String telephone, Date birthdate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.documentType = documentType;
		this.document = document;
		this.telephone = telephone;
		this.birthdate = birthdate;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
}
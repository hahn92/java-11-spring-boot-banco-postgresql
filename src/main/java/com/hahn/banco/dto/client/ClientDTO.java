package com.hahn.banco.dto.client;

import java.sql.Date;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.entity.constant.DocumentType;
import com.hahn.banco.entity.constant.StateType;

public class ClientDTO {

	private String name;
	private String surname;
	private String username;
	private String email;
	private Date birthdate;
	private String telephone;
	private DocumentType documentType;
	private String document;
	private AddressDTO address;
	private StateType state;


	public ClientDTO(String name, String surname, String username, String email, Date birthdate, String telephone,
			DocumentType documentType, String document, AddressDTO address, StateType state) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.birthdate = birthdate;
		this.telephone = telephone;
		this.documentType = documentType;
		this.document = document;
		this.address = address;
		this.state = state;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}
	
}

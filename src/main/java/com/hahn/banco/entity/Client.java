package com.hahn.banco.entity;

import java.sql.Date;
import java.util.ArrayList;
// import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hahn.banco.entity.constant.DocumentType;
import com.hahn.banco.entity.generic.Person;


@Entity
public class Client extends Person {

	@Column(name="username", nullable=false, unique=true, length=20)
	private String username;
	
	@Column(name="email", nullable=false, unique=true, length=20)
	private String email;

	@Column(name="password", nullable=false, length=100)
	private String password;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Account> post = new ArrayList<>();


	public Client() {
		super();
	}
	public Client(Address address, String name, String surname, String telephone, DocumentType documentType, String document, Date birthdate) {
		super(address, name, surname, telephone, documentType, document, birthdate);
	}
	public Client(Address address, String name, String surname, String telephone, DocumentType documentType, 
			String document, Date birthdate, String username, String email, String password) {
		super(address, name, surname, telephone, documentType, document, birthdate);
        this.username = username;
		this.email = email.toLowerCase();
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	public Client(Long id, Address address, String name, String surname, String telephone, DocumentType documentType, 
			String document, Date birthdate, String username, String email, String password) {
		super(id, address, name, surname, telephone, documentType, document, birthdate);
        this.username = username;
		this.email = email.toLowerCase();
		this.password = new BCryptPasswordEncoder().encode(password);
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		this.password = bCryptPasswordEncoder.encode(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


    @Override
	public String toString() {
		return "Client [id=" + getId() + ", username=" + username + ", email=" + email + "name" + getName() 
		+ ", surname=" + getSurname() + ", telephone=" + getTelephone() + ", documentType=" + getDocumentType() 
		+ ", document=" + getDocument() + ", birthdate=" + getBirthdate() + ", state=" + getState() + "]";
	}

	public boolean isValidPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(password, this.password);
	}

	public boolean idOfAge() {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(getBirthdate());
		// return calendar.get(Calendar.YEAR) >= 18;
		return this.getBirthdate().getYear() + 1900 >= 18;
	}

}

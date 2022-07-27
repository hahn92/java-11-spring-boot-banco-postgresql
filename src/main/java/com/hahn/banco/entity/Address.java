package com.hahn.banco.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.hahn.banco.entity.generic.Audit;

@Entity
public class Address extends Audit{
	
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_city", nullable=false)
    private City city;

	@Column(name="street", nullable=false, length=20)
	private Boolean street;
	
	@Column(name="direction", nullable=false, length=50)
	private String direction;

	
	public Address(City city, Boolean street, String direction) {
		super();
		this.city = city;
		this.street = street;
		this.direction = direction;
	}


	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Boolean getStreet() {
		return street;
	}

	public void setStreet(Boolean street) {
		this.street = street;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}


	@Override
	public String toString() {
		return "Address [id=" + getId() + ", city=" + city + ", direction=" + direction + ", street=" + street 
		+ ", state = " + getState() + "]";
	}

	
}

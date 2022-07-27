package com.hahn.banco.dto.address;

import com.hahn.banco.entity.City;
import com.hahn.banco.entity.constant.StateType;

public class AddressDTO {

	private City city;
	private Boolean street;
	private String direction;
	private StateType state;

	
	public AddressDTO(City city, Boolean street, String direction, StateType state) {
		this.city = city;
		this.street = street;
		this.direction = direction;
		this.state = state;
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
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}

}

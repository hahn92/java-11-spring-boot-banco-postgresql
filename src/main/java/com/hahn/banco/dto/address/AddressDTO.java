package com.hahn.banco.dto.address;

import com.hahn.banco.dto.city.CityDTO;
import com.hahn.banco.entity.constant.StateType;

public class AddressDTO {

	private long id;
	private CityDTO city;
	private Boolean street;
	private String direction;
	private StateType state;

	
	public AddressDTO(long id, CityDTO city, Boolean street, String direction, StateType state) {
		this.id = id;
		this.city = city;
		this.street = street;
		this.direction = direction;
		this.state = state;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CityDTO getCity() {
		return city;
	}
	public void setCity(CityDTO city) {
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

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", city=" + city + ", street=" + street + ", direction=" + direction + ", state="
				+ state + "]";
	}

}

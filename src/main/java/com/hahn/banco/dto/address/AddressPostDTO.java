package com.hahn.banco.dto.address;

public class AddressPostDTO {

	private Boolean street;
	private String direction;

	
	public AddressPostDTO(Boolean street, String direction) {
		this.street = street;
		this.direction = direction;
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

}

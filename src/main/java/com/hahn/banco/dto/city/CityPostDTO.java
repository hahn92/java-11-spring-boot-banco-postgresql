package com.hahn.banco.dto.city;

public class CityPostDTO {

	private String name;

	
	public CityPostDTO() {
		super();
	}
	public CityPostDTO(String name) {
		this.name = name;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
    public String toString() {
		return "CityPostDTO{ name=" + name + "}";
	}

}

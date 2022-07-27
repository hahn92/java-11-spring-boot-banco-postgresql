package com.hahn.banco.dto.city;

public class CityPostDTO {

	private long department;
	private String name;

	
	public CityPostDTO(long department, String name) {
		this.department = department;
		this.name = name;
	}

	
	public long getDepartment() {
		return department;
	}
	public void setDepartment(long department) {
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

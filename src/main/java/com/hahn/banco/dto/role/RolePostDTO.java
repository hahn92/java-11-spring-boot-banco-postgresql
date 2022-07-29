package com.hahn.banco.dto.role;

public class RolePostDTO {

	private String name;

	
	public RolePostDTO(String name) {
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
		return "RolePostDTO{ name=" + name + "}";
	}

}

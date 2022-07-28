package com.hahn.banco.dto.role;

import com.hahn.banco.entity.constant.StateType;

public class RoleDTO {

	private long id;
	private String name;
	private StateType state;


	public RoleDTO(long id, String name, StateType state) {
		this.id = id;
		this.name = name;
		this.state = state;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}

}

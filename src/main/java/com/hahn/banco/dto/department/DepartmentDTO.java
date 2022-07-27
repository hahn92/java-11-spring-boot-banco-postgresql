package com.hahn.banco.dto.department;

import com.hahn.banco.entity.constant.StateType;

public class DepartmentDTO {

	private String name;
	private StateType state;

	
	public DepartmentDTO(String name, StateType state) {
		this.name = name;
		this.state = state;
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

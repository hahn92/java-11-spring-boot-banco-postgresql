package com.hahn.banco.dto.department;

import com.hahn.banco.entity.constant.StateType;

public class DepartmentDTO {

	private long id;
	private String name;
	private StateType state;

	
	public DepartmentDTO() {
		super();
	}
	public DepartmentDTO(long id, String name, StateType state) {
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


	@Override
	public String toString() {
		return "DepartmentDTO [id=" + id + ", name=" + name + ", state=" + state + "]";
	}

}

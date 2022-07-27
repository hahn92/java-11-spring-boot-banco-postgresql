package com.hahn.banco.dto.city;

import com.hahn.banco.dto.department.DepartmentDTO;
import com.hahn.banco.entity.constant.StateType;

public class CityDTO {

	private DepartmentDTO department;
	private String name;
	private StateType state;

	
	public CityDTO(DepartmentDTO department, String name, StateType state) {
		this.department = department;
		this.name = name;
		this.state = state;
	}


	public DepartmentDTO getDepartment() {
		return department;
	}
	public void setDepartment(DepartmentDTO department) {
		this.department = department;
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

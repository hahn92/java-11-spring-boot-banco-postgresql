package com.hahn.banco.dto.branchOffice;

import com.hahn.banco.dto.address.AddressDTO;
import com.hahn.banco.dto.employee.EmployeeDTO;
import com.hahn.banco.entity.constant.StateType;

public class BranchOfficeDTO {

	private long id;
	private String name;
	private String code;
	private AddressDTO address;
	private EmployeeDTO employee;
	private StateType state;

	
	public BranchOfficeDTO(long id, String name, String code, AddressDTO address, EmployeeDTO employee, StateType state) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.address = address;
		this.employee = employee;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}


	@Override
	public String toString() {
		return "BranchOfficeDTO [id=" + id + ", name=" + name + ", code=" + code + ", address=" + address + ", employee="
				+ employee + ", state=" + state + "]";
	}
	
}

package com.hahn.banco.dto.transaction;

import com.hahn.banco.entity.constant.StateType;

public class TransactionDTO {

	private long id;
	private StateType state;

	
	public TransactionDTO() {
		super();
	}
	public TransactionDTO(long id, StateType state) {
		this.id = id;
		this.state = state;
	}

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public StateType getState() {
		return state;
	}
	public void setState(StateType state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "TransactionDTO [id=" + id + ", state=" + state + "]";
	}

}

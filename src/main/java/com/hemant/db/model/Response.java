package com.hemant.db.model;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	public boolean status;
	public String message;
	public Employee employee;
	
	public boolean isStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

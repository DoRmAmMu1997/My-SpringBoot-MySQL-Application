package com.hemant.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {
	  @Id
    @GeneratedValue
    @Column(name = "Add_ID")
    private Integer Id;
    @Column(name = "Address1")
    private String address1;
    @Column(name = "Address2")
    private String address2;
    @Column(name = "City")
    private String city;
    @Column(name = "State")
    private String state;
    @Column(name = "PIN")
    private Integer pin;
    @Column(name = "Emp_ID")
    private Integer empId;
	  @Column(name = "Created_By")
    private String createdBy;
    @Column(name = "Created_At")
    private String createdAt;
    @Column(name = "Updated_By")
    private String updatedBy;
    @Column(name = "Updated_At")
    private String updatedAt;
    
    public Address() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }
    
    public String getAddress1() {
    	return address1;
    }
    
    public void setAddress1(String address1) {
    	this.address1 = address1;
    }
    
    public String getAddress2() {
    	return address2;
    }
    
    public void setAddress2(String address2) {
    	this.address2 = address2;
    }
    
    public String getCity() {
    	return city;
    }
    
    public void setCity(String city) {
    	this.city = city;
    }
    
    public String getState() {
    	return state;
    }
    
    public void setState(String state) {
    	this.state = state;
    }
    
    public Integer getPIN() {
    	return pin;
    }
    
    public void setPIN(Integer pin) {
    	this.pin = pin;
    }
    
    public Integer getEmpId() {
    	return empId;
    }
    
    public void setEmpId(Integer empId) {
    	this.empId = empId;
    }
    
    public String getCreatedBy() {
    	return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
    	this.createdBy = createdBy;
    }
    
    public String getCreatedAt() {
    	return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
    	this.createdAt = createdAt;
    }
    
    public String getUpdatedBy() {
    	return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
    	this.updatedBy = updatedBy;
    }
    
    public String getUpdatedAt() {
    	return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
    	this.updatedAt = updatedAt;
    }
}

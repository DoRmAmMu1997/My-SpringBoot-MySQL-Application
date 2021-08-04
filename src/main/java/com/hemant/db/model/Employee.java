package com.hemant.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import com.hemant.db.validators.IsInteger;
import com.hemant.db.validators.IsLong;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "Emp_ID")
    private Integer Id;
    @Column(name = "Name")
    @NotNull
    private String name;
    @NotNull
    @Column(name = "Designation")
    private String designation;
    @NotNull
    @Min(value = 25000)
    @Column(name = "Salary")
    private Integer salary;
    @NotNull
    @IsInteger
    @Column(name = "Dep_ID")
    private Integer depId;
    @NotNull
    @IsLong
    @Min(value = (long)7e9)
    @Max(value = (long)1e10 - 1)
    @Column(name = "Mobile")
    private long mobile;
    @NotNull
    @Email
    @Column(name = "EMail")
    private String email;
    @NotNull
    @Column(name = "Password")
    private String password;
    @Column(name = "Status")
    private String status;
    @NotNull
    @Column(name = "Account_Status")
    private String accountStatus;
    @NotNull
    @Column(name = "Created_By")
    private String createdBy;
    @NotNull
    @Column(name = "Created_At")
    private String createdAt;
    @Column(name = "Updated_By")
    private String updatedBy;
    @Column(name = "Updated_At")
    private String updatedAt;

    public Employee() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String Designation) {
        this.designation = Designation;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
    
    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
    
    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public void setStatus(String status) {
    	this.status = status;
    }
    
    public String getAccountStatus() {
    	return accountStatus;
    }
    
    public void setAccountStatus(String accountStatus) {
    	this.accountStatus = accountStatus;
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

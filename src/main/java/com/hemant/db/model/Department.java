package com.hemant.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import com.hemant.db.validators.IsInteger;

@Entity
public class Department {
    @Id
    @GeneratedValue
    @IsInteger
    @Column(name = "Dep_ID")
    private Integer Id;
    @NotBlank
    @Column(name = "Name")
    private String name;
    @NotBlank
    @Column(name = "Address")
    private String address;
    @NotNull
    @IsInteger
    @Max(value = 200)
    @Column(name = "Floor")
    private Integer floor;
    @NotBlank
    @Column(name = "Created_By")
    private String createdBy;
    @NotBlank
    @Column(name = "Created_At")
    private String createdAt;
    @Column(name = "Updated_By")
    private String updatedBy;
    @Column(name = "Updated_At")
    private String updatedAt;

    public Department() {
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

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public Integer getfloor() {
        return floor;
    }

    public void setfloor(Integer floor) {
        this.floor = floor;
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

package com.hemant.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Profile {
	@Id
	@GeneratedValue
	@Column(name = "Prof_ID")
	private Integer Id;
	@Size(max = 6)
	@NotBlank
	@Column(name = "Gender")
	private String gender;
	@NotBlank
	@Column(name = "DOB")
	private String dob;
	@NotBlank
	@Column(name = "Hobbies")
	private String hobbies;
	@NotNull
	@Column(name = "Emp_ID")
	private Integer empId;
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
    
    public Profile() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }
    
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }
    
    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    
    public Integer getEmpId() {
        return empId;
    }

    public void setDepId(Integer empId) {
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

package com.hemant.db.repository;

import com.hemant.db.model.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<Employee> findByName(@Param("name") String Name);
	
    public List<Employee> findByDesignation(@Param("designation") String Designation);
    
    public Optional<Employee> findById(@Param("Id") Integer Id);
    
    @Modifying
    @Query("update Employee e set e.designation = :designation where e.Id = :Id")
    public List<Employee> updateDesignation(@Param("Id") Integer Id, @Param("designation") String designation);
    
    @Modifying
    @Query("update Employee e set e.mobile = :mobile where e.Id = :Id")
    public List<Employee> updateMobile(@Param("Id") Integer Id, @Param("mobile") long mobile);
    
    @Modifying
    @Query("update Employee e set e.password = :password where e.Id = :Id")
    public List<Employee> updatePassword(@Param("Id") Integer Id, @Param("password") String password);
    
    @Modifying
    public void deleteById(@Param("Id") Integer Id);
}
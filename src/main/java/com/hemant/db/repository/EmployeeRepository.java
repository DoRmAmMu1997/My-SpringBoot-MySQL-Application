package com.hemant.db.repository;

import com.hemant.db.model.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByAccountStatus(String accountStatus);
	
    List<Employee> findByName(String name);
	
    List<Employee> findByDesignation(String designation);
    
    Optional<Employee> findById(Integer id);

    Optional<Employee> findByEmail(String email);
}

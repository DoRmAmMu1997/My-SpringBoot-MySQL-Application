package com.hemant.db.repository;

import com.hemant.db.model.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findByName(String name);
	
    List<Department> findByAddress(String address);
    
    Optional<Department> findById(Integer id);
}

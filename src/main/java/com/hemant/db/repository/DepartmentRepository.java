package com.hemant.db.repository;

import com.hemant.db.model.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public List<Department> findByName(@Param("name") String Name);
	
    public List<Department> findByAddress(@Param("address") String Address);
    
    public Optional<Department> findById(@Param("Id") Integer Id);
    
    @Modifying
    @Query("update Department d set d.floor = :floor where d.Id = :Id")
    public List<Department> updateFloor(@Param("Id") Integer Id, @Param("floor") Integer floor);
    
    @Modifying
    @Query("update Department d set d.address = :address where d.Id = :Id")
    public List<Department> updateAddress(@Param("Id") Integer Id, @Param("address") String address);

    @Modifying
    public void deleteById(@Param("Id") Integer Id);
}

package com.hemant.db.repository;

import com.hemant.db.model.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByCity(String city);
	
	List<Address> findByState(String state);
	
	Optional<Address> findById(Integer id);
	
	List<Address> findByEmpId(Integer empId);
}

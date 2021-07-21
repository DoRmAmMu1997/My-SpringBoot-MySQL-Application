package com.hemant.db.repository;

import com.hemant.db.model.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	public List<Address> findByCity(@Param("city") String city);
	
	public List<Address> findByState(@Param("state") String state);
	
	public Optional<Address> findById(@Param("Id") Integer Id);
	
	@Modifying
    @Query("update Address a set a.address1 = :address1, a.address2 = :address2, a.city = :city, a.state = :state, a.pin = :pin where a.Id = :Id")
    public List<Address> updateAddress(@Param("Id") Integer Id, @Param("address1") String address1, @Param("address2") String address2, @Param("city") String city, @Param("state") String state, @Param("pin") Integer pin);
	
	@Modifying
    public void deleteById(@Param("Id") Integer Id);
}

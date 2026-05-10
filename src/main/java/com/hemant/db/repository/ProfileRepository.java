package com.hemant.db.repository;

import com.hemant.db.model.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    List<Profile> findByGender(String gender);
	
    List<Profile> findByHobbies(String hobbies);
    
    Optional<Profile> findById(Integer id);
}

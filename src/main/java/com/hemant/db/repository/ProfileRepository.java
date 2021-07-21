package com.hemant.db.repository;

import com.hemant.db.model.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    public List<Profile> findByGender(@Param("gender") String Gender);
	
    public List<Profile> findByHobbies(@Param("hobbies") String Hobbies);
    
    public Optional<Profile> findById(@Param("Id") Integer Id);
    
    @Modifying
    @Query("update Profile p set p.hobbies = :hobbies where p.Id = :Id")
    public List<Profile> updateDesignation(@Param("Id") Integer Id, @Param("hobbies") String hobbies);
    
    @Modifying
    public void deleteById(@Param("Id") Integer Id);
}
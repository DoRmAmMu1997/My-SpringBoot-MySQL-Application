package com.hemant.db.resource;

import com.hemant.db.model.Profile;
import com.hemant.db.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/Profile")
public class ProfileResource {

    @Autowired
    ProfileRepository ProfileRepository;

    @GetMapping(value = "/all")
    public List<Profile> getAll() {
        return ProfileRepository.findAll();
    }

    @PostMapping(value = "/insert")
    public List<Profile> persist(@RequestBody final Profile Profile) {
        ProfileRepository.save(Profile);
        return ProfileRepository.findAll();
    }
    
    @GetMapping("/findbygender")
    public List<Profile> fetchDataByGender(@RequestParam("gender") String gender){        
        return ProfileRepository.findByGender(gender);
    }
    
    @GetMapping("/findbyhobbies")
    public List<Profile> fetchDataByHobbies(@RequestParam("hobbies") String hobbies){        
        return ProfileRepository.findByHobbies(hobbies);
    }
    
    @PostMapping("/updatehobbies")
    public List<Profile> updateFloor(@RequestParam("Id") Integer Id, @RequestParam("hobbies") String hobbies) {
    	Profile p = ProfileRepository.findById(Id).get();
    	p.setHobbies(hobbies);
    	ProfileRepository.save(p);
    	return ProfileRepository.findAll();
    }
    
    @DeleteMapping("/delete")
    public List<Profile> deleteProfile(@RequestParam("Id") Integer Id){
    	ProfileRepository.deleteById(Id);
    	return ProfileRepository.findAll();
    }
}
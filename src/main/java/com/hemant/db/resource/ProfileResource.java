package com.hemant.db.resource;

import com.hemant.db.model.Profile;
import com.hemant.db.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

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
    public ResponseEntity<@Valid Profile> persist(@Valid @RequestBody final Profile Profile) {
    	try {
		ProfileRepository.save(Profile);
		return ResponseEntity.ok(Profile);
    	} catch(Exception e) {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping("/findbygender")
    public List<Profile> fetchDataByGender(@Valid @RequestParam("gender") String gender){        
        return ProfileRepository.findByGender(gender);
    }
    
    @GetMapping("/findbyhobbies")
    public List<Profile> fetchDataByHobbies(@Valid @RequestParam("hobbies") String hobbies){        
        return ProfileRepository.findByHobbies(hobbies);
    }
    
    @PostMapping("/updatehobbies")
    public ResponseEntity<Profile> updateHobbies(@RequestParam("Id") Integer Id, @Valid @RequestParam("hobbies") String hobbies) {
	    Profile p = ProfileRepository.findById(Id).get();
	    p.setHobbies(hobbies);
	    ProfileRepository.save(p);
	    return ResponseEntity.ok(p);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProfile(@RequestParam("Id") Integer Id){
	    try {
	    	ProfileRepository.deleteById(Id);
		    return ResponseEntity.ok("Success");
	    } catch(Exception e) {
	    	return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

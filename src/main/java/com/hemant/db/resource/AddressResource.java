package com.hemant.db.resource;

import com.hemant.db.model.Address;
import com.hemant.db.repository.AddressRepository;
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
@RequestMapping(value = "/rest/Address")
public class AddressResource {
	
	@Autowired
    AddressRepository AddressRepository;

    @GetMapping(value = "/all")
    public List<Address> getAll() {
        return AddressRepository.findAll();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<List<Address>> persist(@Valid @RequestBody final Address Address) {
	    AddressRepository.save(Address);
	    return ResponseEntity.ok(AddressRepository.findByEmp(Address.getEmpId()));
    }  
    
    @GetMapping(value = "/findbycity")
    public List<Address> fetchDataByCity(@Valid @RequestParam("city") String city) {
    	return AddressRepository.findByCity(city);
    }
    
    @GetMapping(value = "/findbystate")
    public List<Address> fetchDataByState(@Valid @RequestParam("state") String state) {
    	return AddressRepository.findByState(state);
    }
    
    @PostMapping("/updateaddress")
    public ResponseEntity<Address> updateAddress(@RequestParam("Id") Integer Id, @Valid @RequestParam("address1") String address1, @Valid @RequestParam("address2") String address2, @Valid @RequestParam("city") String city, @Valid @RequestParam("state") String state, @Valid @RequestParam("pin") Integer pin) {
	    Address a = AddressRepository.findById(Id).get();
	    a.setAddress1(address1); a.setAddress2(address2); a.setCity(city); a.setState(state); a.setPIN(pin);
	    AddressRepository.save(a);
	    return ResponseEntity.ok(a);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("Id") Integer Id){
    	try {
	    	AddressRepository.deleteById(Id);
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

package com.hemant.db.resource;

import com.hemant.db.model.Employee;
import com.hemant.db.repository.EmployeeRepository;
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
@RequestMapping(value = "/rest/Employee")
public class EmployeeResource {

    @Autowired
    EmployeeRepository EmployeeRepository;

    @GetMapping(value = "/all")
    public List<Employee> getAll() {
        return EmployeeRepository.findAll();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<String> persist(@Valid @RequestBody final Employee Employee) {
	    EmployeeRepository.save(Employee);
	    return ResponseEntity.ok("Success");
    }  
    
    @GetMapping("/findbyname")
    public List<Employee> fetchDataByName(@Valid @RequestParam("name") String name){        
        return EmployeeRepository.findByName(name);
    }
    
    @GetMapping("/findbydesignation")
    public List<Employee> fetchDataByDesignation(@Valid @RequestParam("designation") String designation){        
        return EmployeeRepository.findByDesignation(designation);
    }
    
    @PostMapping("/updatedesignation")
    public ResponseEntity<String> updateDesignation(@RequestParam("Id") Integer Id, @Valid @RequestParam("designation") String designation) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setDesignation(designation);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok("Success");
    }
    
    @PostMapping("/updatemobile")
    public ResponseEntity<String> updateMobile(@RequestParam("Id") Integer Id, @Valid @RequestParam("mobile") long mobile) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setMobile(mobile);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok("Success");
    }
    
    @PostMapping("/updatepassword")
    public ResponseEntity<String> updatePassword(@RequestParam("Id") Integer Id, @Valid @RequestParam("password") String password) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setPassword(password);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok("Success");
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("Id") Integer Id){
    	try {
	    	EmployeeRepository.deleteById(Id);
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

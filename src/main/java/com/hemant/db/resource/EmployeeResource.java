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
import java.util.Optional;
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
    public ResponseEntity<@Valid Employee> persist(@Valid @RequestBody final Employee Employee) {
	    try {
	    	EmployeeRepository.save(Employee);
		return ResponseEntity.ok(Employee);
	    } catch(Exception e) {
	    	Employee E = EmployeeRepository.findByEmail(Employee.getEmail());
	    	E.setAccountStatus("Activated");
	    	EmployeeRepository.save(E);
	    	return ResponseEntity.ok(E);
	    }
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
    public ResponseEntity<Employee> updateDesignation(@RequestParam("Id") Integer Id, @Valid @RequestParam("designation") String designation) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setDesignation(designation);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok(e);
    }
    
    @PostMapping("/updatemobile")
    public ResponseEntity<Employee> updateMobile(@RequestParam("Id") Integer Id, @Valid @RequestParam("mobile") long mobile) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setMobile(mobile);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok(e);
    }
    
    @PostMapping("/updatepassword")
    public ResponseEntity<Employee> updatePassword(@RequestParam("Id") Integer Id, @Valid @RequestParam("password") String password) {
	    Employee e = EmployeeRepository.findById(Id).get();
	    e.setPassword(password);
	    EmployeeRepository.save(e);
	    return ResponseEntity.ok(e);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Employee> deleteUser(@RequestParam("Id") Integer Id){
    	Optional<Employee> o = EmployeeRepository.findById(Id);
    	if(o.isPresent()) {
	    	Employee e = o.get();
	    	e.setAccountStatus("Deactivated");
	    	EmployeeRepository.save(e);
	    	return ResponseEntity.ok(e);
    	}
    	else {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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

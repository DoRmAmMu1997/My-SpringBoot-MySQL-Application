package com.hemant.db.resource;

import com.hemant.db.model.Department;
import com.hemant.db.repository.DepartmentRepository;
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
@RequestMapping(value = "/rest/Department")
public class DepartmentResource {

    @Autowired
    DepartmentRepository DepartmentRepository;

    @GetMapping(value = "/all")
    public List<Department> getAll() {
        return DepartmentRepository.findAll();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<String> persist(@Valid @RequestBody final Department Department) {
	    DepartmentRepository.save(Department);
	    return ResponseEntity.ok("Success");
    }
    
    @GetMapping("/findbyname")
    public List<Department> fetchDataByName(@Valid @RequestParam("name") String name){        
        return DepartmentRepository.findByName(name);
    }
    
    @GetMapping("/findbyaddress")
    public List<Department> fetchDataByAddress(@Valid @RequestParam("address") String address){        
        return DepartmentRepository.findByAddress(address);
    }
    
    @PostMapping("/updatefloor")
    public ResponseEntity<String> updateFloor(@RequestParam("Id") Integer Id, @Valid @RequestParam("floor") Integer floor) {
	    Department d = DepartmentRepository.findById(Id).get();
	    d.setfloor(floor);
	    DepartmentRepository.save(d);
	    return ResponseEntity.ok("Success");
    }
    
    @PostMapping("/updateaddress")
    public ResponseEntity<String> updateAddress(@RequestParam("Id") Integer Id, @Valid @RequestParam("address") String address) {
	    Department d = DepartmentRepository.findById(Id).get();
	    d.setaddress(address);
	    DepartmentRepository.save(d);
	    return ResponseEntity.ok("Success");
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDepartment(@RequestParam("Id") Integer Id){
    	try {
	    	DepartmentRepository.deleteById(Id);
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

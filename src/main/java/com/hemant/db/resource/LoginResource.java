package com.hemant.db.resource;

import com.hemant.db.model.Employee;
import com.hemant.db.repository.EmployeeRepository;
import com.hemant.db.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/rest/Login")
public class LoginResource {
	
	@Autowired
	EmployeeRepository EmployeeRepository;
	
	@GetMapping("/findbyemail")
	public Employee fetchByEmail(@Valid @RequestParam("email") String email) {
		return EmployeeRepository.findByEmail(email);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@Valid @RequestParam("email") String email, @Valid @RequestParam("password") String password) {
		Response r = new Response();
		Employee e = EmployeeRepository.findByEmail(email);
		try {
			String correctPassword = e.getPassword();
			if(e.getStatus().equals("Logged In")) {
				r.setStatus(false);
				r.setEmployee(e);
				return new ResponseEntity<>(r, HttpStatus.FORBIDDEN);
			} 
			else {
				if(password.equals(correctPassword)) {
					e.setStatus("Logged In");
					EmployeeRepository.save(e);
					r.setStatus(true);
					r.setEmployee(e);
					return new ResponseEntity<>(r, HttpStatus.OK);
				}
				else {
					r.setStatus(false);
					r.setMessage("Incorrect Email or Password");
					return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
				}
			}
		} catch(Exception e1) {
			r.setStatus(false);
			r.setMessage("Something went wrong " + e1.getMessage());
			return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestParam("email") String email) {
		Employee e = EmployeeRepository.findByEmail(email);
		try {
			if(e.getStatus().equals("Logged In")) {
				e.setStatus("Logged Out");
				EmployeeRepository.save(e);
				return new ResponseEntity<>("Logged Out", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("Error: User Already logged out", HttpStatus.FORBIDDEN);
			}
		} catch(Exception e1) {
			return new ResponseEntity<>("Error: " + e1.getMessage(), HttpStatus.BAD_REQUEST);
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

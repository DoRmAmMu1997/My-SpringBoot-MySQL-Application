package com.hemant.db.resource;

import com.hemant.db.model.Employee;
import com.hemant.db.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Employee> persist(@RequestBody final Employee Employee) {
        EmployeeRepository.save(Employee);
        return EmployeeRepository.findAll();
    }  
    
    @GetMapping("/findbyname")
    public List<Employee> fetchDataByName(@RequestParam("name") String name){        
        return EmployeeRepository.findByName(name);
    }
    
    @GetMapping("/findbydesignation")
    public List<Employee> fetchDataByDesignation(@RequestParam("designation") String designation){        
        return EmployeeRepository.findByDesignation(designation);
    }
    
    @PostMapping("/updatedesignation")
    public List<Employee> updateDesignation(@RequestParam("Id") Integer Id, @RequestParam("designation") String designation) {
    	Employee e = EmployeeRepository.findById(Id).get();
    	e.setDesignation(designation);
    	EmployeeRepository.save(e);
    	return EmployeeRepository.findAll();
    }
    
    @PostMapping("/updatemobile")
    public List<Employee> updateMobile(@RequestParam("Id") Integer Id, @RequestParam("mobile") long mobile) {
    	Employee e = EmployeeRepository.findById(Id).get();
    	e.setMobile(mobile);
    	EmployeeRepository.save(e);
    	return EmployeeRepository.findAll();
    }
    
    @PostMapping("/updatepassword")
    public List<Employee> updatePassword(@RequestParam("Id") Integer Id, @RequestParam("password") String password) {
    	Employee e = EmployeeRepository.findById(Id).get();
    	e.setPassword(password);
    	EmployeeRepository.save(e);
    	return EmployeeRepository.findAll();
    }
    
    @DeleteMapping("/delete")
    public List<Employee> deleteUser(@RequestParam("Id") Integer Id){
    	EmployeeRepository.deleteById(Id);
    	return EmployeeRepository.findAll();
    }
}

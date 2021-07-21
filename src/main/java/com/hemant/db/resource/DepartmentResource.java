package com.hemant.db.resource;

import com.hemant.db.model.Department;
import com.hemant.db.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public List<Department> persist(@RequestBody final Department Department) {
        DepartmentRepository.save(Department);
        return DepartmentRepository.findAll();
    }
    
    @GetMapping("/findbyname")
    public List<Department> fetchDataByName(@RequestParam("name") String name){        
        return DepartmentRepository.findByName(name);
    }
    
    @GetMapping("/findbyaddress")
    public List<Department> fetchDataByDesignation(@RequestParam("address") String address){        
        return DepartmentRepository.findByAddress(address);
    }
    
    @PostMapping("/updatefloor")
    public List<Department> updateFloor(@RequestParam("Id") Integer Id, @RequestParam("floor") Integer floor) {
    	Department d = DepartmentRepository.findById(Id).get();
    	d.setfloor(floor);
    	DepartmentRepository.save(d);
    	return DepartmentRepository.findAll();
    }
    
    @PostMapping("/updateaddress")
    public List<Department> updateAddress(@RequestParam("Id") Integer Id, @RequestParam("address") String address) {
    	Department d = DepartmentRepository.findById(Id).get();
    	d.setaddress(address);
    	DepartmentRepository.save(d);
    	return DepartmentRepository.findAll();
    }
    
    @DeleteMapping("/delete")
    public List<Department> deleteDepartment(@RequestParam("Id") Integer Id){
    	DepartmentRepository.deleteById(Id);
    	return DepartmentRepository.findAll();
    }
}

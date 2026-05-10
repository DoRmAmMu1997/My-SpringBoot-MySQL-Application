package com.hemant.db.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.db.model.Employee;
import com.hemant.db.service.EmployeeService;

@Validated
@RestController
@RequestMapping(value = "/rest/Employee")
public class EmployeeResource {
    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/all")
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Employee> persist(@Valid @RequestBody final Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @GetMapping("/findbyname")
    public List<Employee> fetchDataByName(@RequestParam("name") @NotBlank String name) {
        return employeeService.findByName(name);
    }

    @GetMapping("/findbydesignation")
    public List<Employee> fetchDataByDesignation(@RequestParam("designation") @NotBlank String designation) {
        return employeeService.findByDesignation(designation);
    }

    @PostMapping("/updatedesignation")
    public ResponseEntity<Employee> updateDesignation(
            @RequestParam("Id") Integer id,
            @RequestParam("designation") @NotBlank String designation) {
        return ResponseEntity.ok(employeeService.updateDesignation(id, designation));
    }

    @PostMapping("/updatemobile")
    public ResponseEntity<Employee> updateMobile(
            @RequestParam("Id") Integer id,
            @RequestParam("mobile") long mobile) {
        return ResponseEntity.ok(employeeService.updateMobile(id, mobile));
    }

    @PostMapping("/updatepassword")
    public ResponseEntity<Employee> updatePassword(
            @RequestParam("Id") Integer id,
            @RequestParam("password") @NotBlank String password) {
        return ResponseEntity.ok(employeeService.updatePassword(id, password));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Employee> deleteUser(@RequestParam("Id") Integer id) {
        return ResponseEntity.ok(employeeService.deactivateEmployee(id));
    }
}

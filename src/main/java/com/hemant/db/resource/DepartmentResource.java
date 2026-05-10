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

import com.hemant.db.model.Department;
import com.hemant.db.service.DepartmentService;

@Validated
@RestController
@RequestMapping(value = "/rest/Department")
public class DepartmentResource {
    private final DepartmentService departmentService;

    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/all")
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Department> persist(@Valid @RequestBody final Department department) {
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }

    @GetMapping("/findbyname")
    public List<Department> fetchDataByName(@RequestParam("name") @NotBlank String name) {
        return departmentService.findByName(name);
    }

    @GetMapping("/findbyaddress")
    public List<Department> fetchDataByAddress(@RequestParam("address") @NotBlank String address) {
        return departmentService.findByAddress(address);
    }

    @PostMapping("/updatefloor")
    public ResponseEntity<Department> updateFloor(
            @RequestParam("Id") Integer id,
            @RequestParam("floor") Integer floor) {
        return ResponseEntity.ok(departmentService.updateFloor(id, floor));
    }

    @PostMapping("/updateaddress")
    public ResponseEntity<Department> updateAddress(
            @RequestParam("Id") Integer id,
            @RequestParam("address") @NotBlank String address) {
        return ResponseEntity.ok(departmentService.updateAddress(id, address));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDepartment(@RequestParam("Id") Integer id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Success");
    }
}

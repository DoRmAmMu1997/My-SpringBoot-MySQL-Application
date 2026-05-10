package com.hemant.db.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Department;
import com.hemant.db.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<Department> findByAddress(String address) {
        return departmentRepository.findByAddress(address);
    }

    public Department updateFloor(Integer id, Integer floor) {
        Department department = findById(id);
        department.setFloor(floor);
        return departmentRepository.save(department);
    }

    public Department updateAddress(Integer id, String address) {
        Department department = findById(id);
        department.setAddress(address);
        return departmentRepository.save(department);
    }

    public void deleteDepartment(Integer id) {
        Department department = findById(id);
        departmentRepository.delete(department);
    }

    private Department findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
    }
}

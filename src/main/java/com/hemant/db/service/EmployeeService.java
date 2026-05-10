package com.hemant.db.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hemant.db.exception.DuplicateResourceException;
import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Employee;
import com.hemant.db.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findByAccountStatus("Activated");
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.findByEmail(employee.getEmail())
                .map(existingEmployee -> reactivateOrRejectDuplicate(existingEmployee, employee.getEmail()))
                .orElseGet(() -> employeeRepository.save(employee));
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> findByDesignation(String designation) {
        return employeeRepository.findByDesignation(designation);
    }

    public Employee updateDesignation(Integer id, String designation) {
        Employee employee = findById(id);
        employee.setDesignation(designation);
        return employeeRepository.save(employee);
    }

    public Employee updateMobile(Integer id, long mobile) {
        Employee employee = findById(id);
        employee.setMobile(mobile);
        return employeeRepository.save(employee);
    }

    public Employee updatePassword(Integer id, String password) {
        Employee employee = findById(id);
        employee.setPassword(password);
        return employeeRepository.save(employee);
    }

    public Employee deactivateEmployee(Integer id) {
        Employee employee = findById(id);
        employee.setAccountStatus("Deactivated");
        return employeeRepository.save(employee);
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email " + email));
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", id));
    }

    private Employee reactivateOrRejectDuplicate(Employee existingEmployee, String email) {
        if ("Deactivated".equalsIgnoreCase(existingEmployee.getAccountStatus())) {
            existingEmployee.setAccountStatus("Activated");
            return employeeRepository.save(existingEmployee);
        }
        throw new DuplicateResourceException("Employee already exists with email " + email);
    }
}

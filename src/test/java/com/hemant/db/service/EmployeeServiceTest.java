package com.hemant.db.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Employee;
import com.hemant.db.repository.EmployeeRepository;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeServiceTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void cleanDatabase() {
        employeeRepository.deleteAll();
    }

    @Test
    void createEmployeePersistsAndHidesDeactivatedRecordsFromList() {
        Employee activeEmployee = sampleEmployee("active@example.com", "Activated");
        Employee deactivatedEmployee = sampleEmployee("inactive@example.com", "Deactivated");

        Employee savedActiveEmployee = employeeService.createEmployee(activeEmployee);
        employeeService.createEmployee(deactivatedEmployee);

        assertThat(savedActiveEmployee.getId()).isNotNull();
        assertThat(employeeService.getAllEmployees())
                .extracting(Employee::getEmail)
                .containsExactly("active@example.com");
    }

    @Test
    void updateMissingEmployeeThrowsNotFound() {
        assertThatThrownBy(() -> employeeService.updateDesignation(999, "Manager"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Employee not found with id 999");
    }

    private Employee sampleEmployee(String email, String accountStatus) {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setDesignation("Developer");
        employee.setSalary(50000);
        employee.setDepId(1);
        employee.setMobile(9876543210L);
        employee.setEmail(email);
        employee.setPassword("secret");
        employee.setStatus("Logged Out");
        employee.setAccountStatus(accountStatus);
        employee.setCreatedBy("test");
        employee.setCreatedAt("2026-05-10");
        return employee;
    }
}

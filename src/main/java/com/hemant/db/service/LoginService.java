package com.hemant.db.service;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hemant.db.exception.BadRequestException;
import com.hemant.db.exception.ResourceNotFoundException;
import com.hemant.db.model.Employee;
import com.hemant.db.model.Response;
import com.hemant.db.repository.EmployeeRepository;

@Service
@Transactional
public class LoginService {
    private final EmployeeRepository employeeRepository;

    public LoginService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email " + email));
    }

    public LoginResult login(String email, String password) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Incorrect Email or Password"));

        Response response = new Response();
        response.setEmployee(employee);

        if ("Logged In".equals(employee.getStatus())) {
            response.setStatus(false);
            response.setMessage("User is already logged in");
            return new LoginResult(response, HttpStatus.FORBIDDEN);
        }

        if (!password.equals(employee.getPassword())) {
            response.setStatus(false);
            response.setMessage("Incorrect Email or Password");
            response.setEmployee(null);
            return new LoginResult(response, HttpStatus.BAD_REQUEST);
        }

        employee.setStatus("Logged In");
        employeeRepository.save(employee);
        response.setStatus(true);
        response.setMessage("Logged In");
        response.setEmployee(employee);
        return new LoginResult(response, HttpStatus.OK);
    }

    public String logout(String email) {
        Employee employee = findByEmail(email);
        if (!"Logged In".equals(employee.getStatus())) {
            throw new BadRequestException("User is already logged out");
        }

        employee.setStatus("Logged Out");
        employeeRepository.save(employee);
        return "Logged Out";
    }

    public static class LoginResult {
        private final Response response;
        private final HttpStatus status;

        public LoginResult(Response response, HttpStatus status) {
            this.response = response;
            this.status = status;
        }

        public Response getResponse() {
            return response;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }
}

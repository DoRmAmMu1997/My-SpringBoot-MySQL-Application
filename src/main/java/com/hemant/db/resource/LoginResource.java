package com.hemant.db.resource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hemant.db.model.Employee;
import com.hemant.db.model.Response;
import com.hemant.db.service.LoginService;
import com.hemant.db.service.LoginService.LoginResult;

@Validated
@RestController
@RequestMapping(value = "/rest/Login")
public class LoginResource {
    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/findbyemail")
    public Employee fetchByEmail(@RequestParam("email") @Email String email) {
        return loginService.findByEmail(email);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(
            @RequestParam("email") @Email String email,
            @RequestParam("password") @NotBlank String password) {
        LoginResult result = loginService.login(email, password);
        return new ResponseEntity<>(result.getResponse(), result.getStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam("email") @Email String email) {
        return ResponseEntity.ok(loginService.logout(email));
    }
}

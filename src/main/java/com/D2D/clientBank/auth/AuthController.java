package com.D2D.clientBank.auth;

import com.D2D.clientBank.customer.api.dto.CustomerRequest;
import com.D2D.clientBank.customer.db.Customer;
import com.D2D.clientBank.customer.service.CustomerService;
import com.D2D.clientBank.enums.Role;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class AuthController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(CustomerService customerService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/auth")
    public ResponseEntity<String> register(@RequestBody @Valid CustomerRequest customerRequest) {
        log.info("Registering with email: {}", customerRequest.getEmail());
        if (customerService.findByEmail(customerRequest.getEmail()).isPresent()) {
            log.warn("User with email {} exists", customerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User exists");
        }

        Customer newCustomer = new Customer(
                customerRequest.getName(),
                customerRequest.getEmail(),
                customerRequest.getAge(),
                passwordEncoder.encode(customerRequest.getPassword().getPassword()),
                customerRequest.getPhoneNumber(),
                customerRequest.getRole() != null ? customerRequest.getRole() : Role.USER
        );
        customerService.save(newCustomer);
        log.info("Successfully registered with email: {}", customerRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}


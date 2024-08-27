package com.D2D.clientBank.customer.api;

import com.D2D.clientBank.account.api.dto.AccountRequest;
import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.customer.api.dto.CustomerRequest;
import com.D2D.clientBank.customer.api.dto.CustomerResponse;
import com.D2D.clientBank.customer.service.CustomerFacade;
import com.D2D.clientBank.customer.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        log.info("Creating customer with request: {}", customerRequest);
        try {
            CustomerResponse customerResponse = customerFacade.createCustomer(customerRequest);
            log.info("Customer created successfully: {}", customerResponse);
            return ResponseEntity.ok(customerResponse);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create customer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        log.info("Updating customer with ID {} and request: {}", id, customerRequest);
        CustomerResponse customerResponse = customerFacade.updateCustomer(id, customerRequest);
        if (customerResponse != null) {
            log.info("Customer updated successfully: {}", customerResponse);
            return ResponseEntity.ok(customerResponse);
        } else {
            log.warn("Customer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable long id) {
        log.info("Deleting customer with ID {}", id);
        boolean deleted = customerFacade.deleteCustomer(id);
        if (deleted) {
            log.info("Customer with ID {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Customer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @JsonView(Views.DetailInfo.class)
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        log.info("Retrieving customer with ID {}", id);
        CustomerResponse customerResponse = customerFacade.getCustomer(id);
        if (customerResponse != null) {
            log.info("Customer retrieved: {}", customerResponse);
            return ResponseEntity.ok(customerResponse);
        } else {
            log.warn("Customer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/me")
    @JsonView(Views.DetailInfo.class)
    public ResponseEntity<CustomerResponse> getCurrentCustomer(Authentication authentication) {
        String email = authentication.getName();
        log.info("Retrieving current customer");
        CustomerResponse customerResponse = customerFacade.getCurrentCustomer(email);
        if (customerResponse != null) {
            log.info("Current customer retrieved: {}", customerResponse);
            return ResponseEntity.ok(customerResponse);
        } else {
            log.warn("Current customer not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @JsonView(Views.Info.class)
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null || !userDetails.isEnabled()) {
            log.warn("Unauthorized access attempt by anonymous or disabled user");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            Page<CustomerResponse> customerPage = customerFacade.getAllCustomers(PageRequest.of(page, size), userDetails.getUsername());
            log.info("Customers: {}", customerPage);
            return ResponseEntity.ok(customerPage);
        } catch (Exception e) {
            log.error("Error occurred while fetching customers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{customerId}/accounts")
    public ResponseEntity<AccountResponse> createAccountForCustomer(@PathVariable Long customerId, @RequestBody AccountRequest accountRequest) {
        log.info("Creating account for customer with ID {}: {}", customerId, accountRequest);
        try {
            AccountResponse accountResponse = customerFacade.createAccountForCustomer(customerId, accountRequest);
            log.info("Account created successfully for customer ID {}: {}", customerId, accountResponse);
            return ResponseEntity.ok(accountResponse);
        } catch (Exception e) {
            log.error("Error creating account for customer ID {}: {}", customerId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{customerId}/accounts/{accountId}")
    public ResponseEntity<Boolean> deleteAccountFromCustomer(@PathVariable Long customerId, @PathVariable Long accountId) {
        log.info("Deleting account with ID {} from customer ID {}", accountId, customerId);
        boolean deleted = customerFacade.deleteAccountFromCustomer(customerId, accountId);
        if (deleted) {
            log.info("Account with ID {} deleted successfully from customer ID {}", accountId, customerId);
            return ResponseEntity.ok(true);
        } else {
            log.warn("Account with ID {} not found for customer ID {}", accountId, customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}









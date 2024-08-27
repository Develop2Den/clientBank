package com.D2D.clientBank.employer.api;

import com.D2D.clientBank.employer.api.dto.EmployerRequest;
import com.D2D.clientBank.employer.api.dto.EmployerResponse;
import com.D2D.clientBank.employer.service.EmployerFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
public class EmployerController {

    private final EmployerFacade employerFacade;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(@Valid @RequestBody EmployerRequest employerRequest) {
        log.info("Creating employer with request: {}", employerRequest);
        EmployerResponse employerResponse = employerFacade.createEmployer(employerRequest);
        log.info("Employer created successfully: {}", employerResponse);
        return ResponseEntity.ok(employerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerResponse> updateEmployer(@PathVariable Long id, @Valid @RequestBody EmployerRequest employerRequest) {
        log.info("Updating employer with ID {} and request: {}", id, employerRequest);
        EmployerResponse employerResponse = employerFacade.updateEmployer(id, employerRequest);
        if (employerResponse != null) {
            log.info("Employer updated successfully: {}", employerResponse);
            return ResponseEntity.ok(employerResponse);
        } else {
            log.warn("Employer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        log.info("Deleting employer with ID {}", id);
        boolean deleted = employerFacade.deleteEmployer(id);
        if (deleted) {
            log.info("Employer with ID {} deleted", id);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Employer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        log.info("Retrieving employer with ID {}", id);
        EmployerResponse employerResponse = employerFacade.getEmployerById(id);
        if (employerResponse != null) {
            log.info("Employer retrieved: {}", employerResponse);
            return ResponseEntity.ok(employerResponse);
        } else {
            log.warn("Employer with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployerResponse>> getAllEmployers() {
        log.info("Retrieving all employers");
        List<EmployerResponse> employers = employerFacade.getAllEmployers();
        log.info("Employers retrieved: {}", employers);
        return ResponseEntity.ok(employers);
    }
}





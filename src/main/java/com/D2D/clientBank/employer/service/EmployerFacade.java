package com.D2D.clientBank.employer.service;

import com.D2D.clientBank.employer.api.dto.EmployerRequest;
import com.D2D.clientBank.employer.api.dto.EmployerResponse;
import com.D2D.clientBank.employer.db.Employer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployerFacade {

    private final EmployerService employerService;
    private final ModelMapper modelMapper;

    public EmployerResponse createEmployer(EmployerRequest employerRequest) {
        Employer employer = modelMapper.map(employerRequest, Employer.class);
        Employer savedEmployer = employerService.save(employer);
        return modelMapper.map(savedEmployer, EmployerResponse.class);
    }

    public EmployerResponse updateEmployer(long id, EmployerRequest employerRequest) {
        Employer employer = modelMapper.map(employerRequest, Employer.class);
        employer.setId(id);
        Employer updatedEmployer = employerService.save(employer);
        return modelMapper.map(updatedEmployer, EmployerResponse.class);
    }

    public EmployerResponse getEmployerById(long id) {
        Employer employer = employerService.getOne(id);
        return modelMapper.map(employer, EmployerResponse.class);
    }

    public List<EmployerResponse> getAllEmployers() {
        List<Employer> employers = employerService.findAll();
        return employers.stream()
                .map(employer -> modelMapper.map(employer, EmployerResponse.class))
                .collect(Collectors.toList());
    }

    public boolean deleteEmployer(long id) {
        return employerService.deleteById(id);
    }
}



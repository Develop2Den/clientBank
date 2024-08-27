package com.D2D.clientBank.employer.service;

import com.D2D.clientBank.employer.db.Employer;
import com.D2D.clientBank.employer.db.EmployerDAORepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class EmployerService {

    private final EmployerDAORepository employerDAORepository;

    public Employer save(Employer employer) {
        return employerDAORepository.save(employer);
    }

    public boolean deleteById(long id) {
        if (employerDAORepository.existsById(id)) {
            employerDAORepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Employer getOne(long id) {
        return employerDAORepository.findById(id).orElse(null);
    }

    public List<Employer> findAll() {
        return employerDAORepository.findAll();
    }
}







package com.D2D.clientBank.employer;

import com.D2D.clientBank.employer.db.Employer;
import com.D2D.clientBank.employer.db.EmployerDAORepository;
import com.D2D.clientBank.employer.service.EmployerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EmployerServiceTest {

    @Mock
    private EmployerDAORepository employerDAORepository;

    @InjectMocks
    private EmployerService employerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Employer employer = new Employer();
        when(employerDAORepository.save(any(Employer.class))).thenReturn(employer);

        Employer savedEmployer = employerService.save(employer);

        assertEquals(employer, savedEmployer);
        verify(employerDAORepository, times(1)).save(employer);
    }

    @Test
    void testDeleteByIdExists() {
        when(employerDAORepository.existsById(anyLong())).thenReturn(true);

        boolean result = employerService.deleteById(1L);

        assertEquals(true, result);
        verify(employerDAORepository, times(1)).existsById(1L);
        verify(employerDAORepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdDoesNotExist() {
        when(employerDAORepository.existsById(anyLong())).thenReturn(false);

        boolean result = employerService.deleteById(1L);

        assertEquals(false, result);
        verify(employerDAORepository, times(1)).existsById(1L);
        verify(employerDAORepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetOneExists() {
        Employer employer = new Employer();
        when(employerDAORepository.findById(anyLong())).thenReturn(Optional.of(employer));

        Employer foundEmployer = employerService.getOne(1L);

        assertEquals(employer, foundEmployer);
        verify(employerDAORepository, times(1)).findById(1L);
    }

    @Test
    void testGetOneDoesNotExist() {
        when(employerDAORepository.findById(anyLong())).thenReturn(Optional.empty());

        Employer foundEmployer = employerService.getOne(1L);

        assertNull(foundEmployer);
        verify(employerDAORepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Employer> employers = List.of(new Employer(), new Employer());
        when(employerDAORepository.findAll()).thenReturn(employers);

        List<Employer> foundEmployers = employerService.findAll();

        assertEquals(employers, foundEmployers);
        verify(employerDAORepository, times(1)).findAll();
    }
}



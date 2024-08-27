package com.D2D.clientBank.employer;

import com.D2D.clientBank.employer.api.EmployerController;
import com.D2D.clientBank.employer.api.dto.EmployerRequest;
import com.D2D.clientBank.employer.api.dto.EmployerResponse;
import com.D2D.clientBank.employer.service.EmployerFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EmployerControllerTest {

    @Mock
    private EmployerFacade employerFacade;

    @InjectMocks
    private EmployerController employerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployer() {
        EmployerRequest employerRequest = new EmployerRequest();
        EmployerResponse employerResponse = new EmployerResponse();
        when(employerFacade.createEmployer(any(EmployerRequest.class))).thenReturn(employerResponse);

        ResponseEntity<EmployerResponse> response = employerController.createEmployer(employerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employerResponse, response.getBody());
        verify(employerFacade, times(1)).createEmployer(employerRequest);
    }

    @Test
    void testUpdateEmployer() {
        EmployerRequest employerRequest = new EmployerRequest();
        EmployerResponse employerResponse = new EmployerResponse();
        when(employerFacade.updateEmployer(anyLong(), any(EmployerRequest.class))).thenReturn(employerResponse);

        ResponseEntity<EmployerResponse> response = employerController.updateEmployer(1L, employerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employerResponse, response.getBody());
        verify(employerFacade, times(1)).updateEmployer(1L, employerRequest);
    }

    @Test
    void testDeleteEmployerExists() {
        when(employerFacade.deleteEmployer(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = employerController.deleteEmployer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employerFacade, times(1)).deleteEmployer(1L);
    }

    @Test
    void testDeleteEmployerNotFound() {
        when(employerFacade.deleteEmployer(anyLong())).thenReturn(false);

        ResponseEntity<Void> response = employerController.deleteEmployer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(employerFacade, times(1)).deleteEmployer(1L);
    }

    @Test
    void testGetEmployerByIdExists() {
        EmployerResponse employerResponse = new EmployerResponse();
        when(employerFacade.getEmployerById(anyLong())).thenReturn(employerResponse);

        ResponseEntity<EmployerResponse> response = employerController.getEmployerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employerResponse, response.getBody());
        verify(employerFacade, times(1)).getEmployerById(1L);
    }

    @Test
    void testGetEmployerByIdNotFound() {
        when(employerFacade.getEmployerById(anyLong())).thenReturn(null);

        ResponseEntity<EmployerResponse> response = employerController.getEmployerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(employerFacade, times(1)).getEmployerById(1L);
    }

    @Test
    void testGetAllEmployers() {
        List<EmployerResponse> employers = List.of(new EmployerResponse(), new EmployerResponse());
        when(employerFacade.getAllEmployers()).thenReturn(employers);

        ResponseEntity<List<EmployerResponse>> response = employerController.getAllEmployers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employers, response.getBody());
        verify(employerFacade, times(1)).getAllEmployers();
    }
}


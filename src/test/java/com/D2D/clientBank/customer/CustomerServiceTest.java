package com.D2D.clientBank.customer;

import com.D2D.clientBank.customer.db.Customer;
import com.D2D.clientBank.customer.db.CustomerDAORepository;
import com.D2D.clientBank.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerDAORepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer("John Doe", "Doe@do.com", 30, null, "4444444", null);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getOne(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(customer);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(customers, pageable, customers.size());

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<Customer> result = customerService.findAll(pageable);
        assertEquals(1, result.getContent().size());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }

    @Test
    void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.save(customer);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(true);
        customerService.deleteById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }
}


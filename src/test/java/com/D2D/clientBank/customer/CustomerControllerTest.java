package com.D2D.clientBank.customer;

import com.D2D.clientBank.account.api.dto.AccountRequest;
import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.customer.api.CustomerController;
import com.D2D.clientBank.customer.api.dto.CustomerRequest;
import com.D2D.clientBank.customer.api.dto.CustomerResponse;
import com.D2D.clientBank.customer.service.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.D2D.clientBank.enums.Currency.USD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerFacade customerFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void testCreateCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john.doe@example.com");
        customerRequest.setAge(30);
        customerRequest.setPhoneNumber("1234567890");

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe");
        customerResponse.setEmail("john.doe@example.com");
        customerResponse.setAge(30);
        customerResponse.setPhoneNumber("1234567890");

        when(customerFacade.createCustomer(any())).thenReturn(customerResponse);

        mockMvc.perform(post("/api/customers")
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    @WithMockUser
    void testUpdateCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe Updated");
        customerRequest.setEmail("john.doe.updated@example.com");
        customerRequest.setAge(31);
        customerRequest.setPhoneNumber("7654321");

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe Updated");
        customerResponse.setEmail("john.doe.updated@example.com");
        customerResponse.setAge(31);
        customerResponse.setPhoneNumber("7654321");

        when(customerFacade.updateCustomer(anyLong(), any())).thenReturn(customerResponse);

        mockMvc.perform(put("/api/customers/1")
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(customerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.doe.updated@example.com"))
                .andExpect(jsonPath("$.age").value(31))
                .andExpect(jsonPath("$.phoneNumber").value("7654321"));
    }

    @Test
    @WithMockUser
    void testDeleteCustomer() throws Exception {
        when(customerFacade.deleteCustomer(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/customers/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetCustomer() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1L);
        customerResponse.setName("John Doe");
        customerResponse.setEmail("john.doe@example.com");
        customerResponse.setAge(30);
        customerResponse.setPhoneNumber("1234567890");

        when(customerFacade.getCustomer(anyLong())).thenReturn(customerResponse);

        mockMvc.perform(get("/api/customers/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    @WithMockUser
    void testCreateAccountForCustomer() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setCurrency(USD);
        accountRequest.setBalance(100.0);

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(1L);
        accountResponse.setNumber("12345");
        accountResponse.setCurrency(USD);
        accountResponse.setBalance(100.0);

        when(customerFacade.createAccountForCustomer(anyLong(), any())).thenReturn(accountResponse);

        mockMvc.perform(post("/api/customers/1/accounts")
                        .with(csrf())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.number").value("12345"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.balance").value(100.0));
    }

    @Test
    @WithMockUser
    void testDeleteAccountFromCustomer() throws Exception {
        when(customerFacade.deleteAccountFromCustomer(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/customers/1/accounts/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}




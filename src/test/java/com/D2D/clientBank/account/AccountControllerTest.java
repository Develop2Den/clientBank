package com.D2D.clientBank.account;

import com.D2D.clientBank.account.api.AccountController;
import com.D2D.clientBank.account.api.dto.AccountRequest;
import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.account.service.AccountFacade;
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
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountFacade accountFacade;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccountSuccess() {
        AccountRequest accountRequest = new AccountRequest();
        AccountResponse accountResponse = new AccountResponse();
        when(accountFacade.createAccount(any(AccountRequest.class))).thenReturn(accountResponse);

        ResponseEntity<AccountResponse> response = accountController.createAccount(accountRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountResponse, response.getBody());
        verify(accountFacade, times(1)).createAccount(accountRequest);
    }

    @Test
    void testCreateAccountBadRequest() {
        AccountRequest accountRequest = new AccountRequest();
        when(accountFacade.createAccount(any(AccountRequest.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<AccountResponse> response = accountController.createAccount(accountRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(accountFacade, times(1)).createAccount(accountRequest);
    }

    @Test
    void testDepositSuccess() {
        AccountResponse accountResponse = new AccountResponse();
        when(accountFacade.deposit(anyString(), anyDouble())).thenReturn(accountResponse);

        ResponseEntity<AccountResponse> response = accountController.deposit("12345", 100.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountResponse, response.getBody());
        verify(accountFacade, times(1)).deposit("12345", 100.0);
    }

    @Test
    void testDepositBadRequest() {
        when(accountFacade.deposit(anyString(), anyDouble())).thenReturn(null);

        ResponseEntity<AccountResponse> response = accountController.deposit("12345", 100.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(accountFacade, times(1)).deposit("12345", 100.0);
    }

    @Test
    void testWithdrawSuccess() {
        AccountResponse accountResponse = new AccountResponse();
        when(accountFacade.withdraw(anyString(), anyDouble())).thenReturn(accountResponse);

        ResponseEntity<AccountResponse> response = accountController.withdraw("12345", 50.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountResponse, response.getBody());
        verify(accountFacade, times(1)).withdraw("12345", 50.0);
    }

    @Test
    void testWithdrawBadRequest() {
        when(accountFacade.withdraw(anyString(), anyDouble())).thenReturn(null);

        ResponseEntity<AccountResponse> response = accountController.withdraw("12345", 50.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(accountFacade, times(1)).withdraw("12345", 50.0);
    }

    @Test
    void testTransferSuccess() {
        when(accountFacade.transfer(anyString(), anyString(), anyDouble())).thenReturn(true);

        ResponseEntity<String> response = accountController.transfer("12345", "67890", 200.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer successful", response.getBody());
        verify(accountFacade, times(1)).transfer("12345", "67890", 200.0);
    }

    @Test
    void testTransferBadRequest() {
        when(accountFacade.transfer(anyString(), anyString(), anyDouble())).thenReturn(false);

        ResponseEntity<String> response = accountController.transfer("12345", "67890", 200.0);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Transfer failed", response.getBody());
        verify(accountFacade, times(1)).transfer("12345", "67890", 200.0);
    }

    @Test
    void testDeleteAccountSuccess() {
        when(accountFacade.deleteAccount(anyLong())).thenReturn(true);

        ResponseEntity<String> response = accountController.deleteAccount(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Account deleted", response.getBody());
        verify(accountFacade, times(1)).deleteAccount(1L);
    }

    @Test
    void testDeleteAccountNotFound() {
        when(accountFacade.deleteAccount(anyLong())).thenReturn(false);

        ResponseEntity<String> response = accountController.deleteAccount(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Account not found", response.getBody());
        verify(accountFacade, times(1)).deleteAccount(1L);
    }

    @Test
    void testGetAccountSuccess() {
        AccountResponse accountResponse = new AccountResponse();
        when(accountFacade.getAccount(anyLong())).thenReturn(accountResponse);

        ResponseEntity<AccountResponse> response = accountController.getAccount(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountResponse, response.getBody());
        verify(accountFacade, times(1)).getAccount(1L);
    }

    @Test
    void testGetAccountNotFound() {
        when(accountFacade.getAccount(anyLong())).thenReturn(null);

        ResponseEntity<AccountResponse> response = accountController.getAccount(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(accountFacade, times(1)).getAccount(1L);
    }

    @Test
    void testGetAllAccounts() {
        List<AccountResponse> accountResponses = List.of(new AccountResponse(), new AccountResponse());
        when(accountFacade.getAllAccounts()).thenReturn(accountResponses);

        ResponseEntity<List<AccountResponse>> response = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountResponses, response.getBody());
        verify(accountFacade, times(1)).getAllAccounts();
    }
}



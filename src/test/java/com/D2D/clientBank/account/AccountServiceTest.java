package com.D2D.clientBank.account;

import com.D2D.clientBank.account.db.Account;
import com.D2D.clientBank.account.db.AccountDaoRepository;
import com.D2D.clientBank.account.service.AccountService;
import com.D2D.clientBank.customer.db.Customer;
import com.D2D.clientBank.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountDaoRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account("123456", Currency.USD, 100.0, new Customer());
    }

    @Test
    void testGetAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getOne(1L);
        assertNotNull(result);
        assertEquals("123456", result.getNumber());
    }

    @Test
    void testAddAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.save(account);
        assertNotNull(result);
        assertEquals("123456", result.getNumber());
    }

    @Test
    void testDeleteCustomer() {
        Long accountId = 1L;

        when(accountRepository.existsById(accountId)).thenReturn(true);
        accountService.deleteById(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void testDeposit() {
        when(accountRepository.findByNumber("123456")).thenReturn(account);
        accountService.deposit("123456", 50.0);
        assertEquals(150.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testWithdraw() {
        when(accountRepository.findByNumber("123456")).thenReturn(account);
        accountService.withdraw("123456", 50.0);
        assertEquals(50.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testTransfer() {
        Account targetAccount = new Account("67890", Currency.USD, 200.0, new Customer());
        when(accountRepository.findByNumber("123456")).thenReturn(account);
        when(accountRepository.findByNumber("67890")).thenReturn(targetAccount);
        accountService.transfer("123456", "67890", 50.0);
        assertEquals(50.0, account.getBalance());
        assertEquals(250.0, targetAccount.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(accountRepository, times(1)).save(targetAccount);
    }
}


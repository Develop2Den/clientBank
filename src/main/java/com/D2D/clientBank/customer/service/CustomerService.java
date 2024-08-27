package com.D2D.clientBank.customer.service;

import com.D2D.clientBank.account.db.Account;
import com.D2D.clientBank.account.db.AccountDaoRepository;
import com.D2D.clientBank.customer.db.Customer;
import com.D2D.clientBank.customer.db.CustomerDAORepository;
import com.D2D.clientBank.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDAORepository customerDAORepository;
    private final AccountDaoRepository accountDaoRepository;

    public Customer save(Customer customer) {
        return customerDAORepository.save(customer);
    }

    public Optional<Customer> findByEmail(String email) {
        return customerDAORepository.findByEmail(email);
    }

    public List<Customer> findAllByRole(Role role) {
        return customerDAORepository.findAllByRole(role);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (customerDAORepository.existsById(id)) {
            customer.setId(id);
            return customerDAORepository.save(customer);
        }
        return null;
    }

    public boolean deleteById(long id) {
        if (customerDAORepository.existsById(id)) {
            customerDAORepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Customer getOne(long id) {
        return customerDAORepository.findById(id).orElse(null);
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerDAORepository.findAll(pageable);
    }

    public Account saveAccountForCustomer(Long customerId, Account account) {
        Customer customer = customerDAORepository.findById(customerId).orElse(null);
        if (customer != null) {
            account.setNumber(UUID.randomUUID().toString());
            account.setCustomer(customer);
            account.setBalance(account.getBalance() != null ? account.getBalance() : 0.0);
            Account savedAccount = accountDaoRepository.save(account);
            customer.getAccounts().add(savedAccount);
            customerDAORepository.save(customer);
            return savedAccount;
        }
        return null;
    }

    public boolean deleteAccountFromCustomer(Long customerId, Long accountId) {
        Customer customer = customerDAORepository.findById(customerId).orElse(null);
        if (customer != null) {
            Account accountToRemove = customer.getAccounts().stream()
                    .filter(account -> account.getId().equals(accountId))
                    .findFirst().orElse(null);
            if (accountToRemove != null) {
                customer.getAccounts().remove(accountToRemove);
                customerDAORepository.save(customer);
                accountDaoRepository.deleteById(accountId);
                return true;
            }
        }
        return false;
    }
}








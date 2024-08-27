package com.D2D.clientBank.account.service;

import com.D2D.clientBank.account.db.Account;
import com.D2D.clientBank.account.db.AccountDaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AccountService {

    private final AccountDaoRepository accountDaoRepository;

    public Account save(Account account) {
        return accountDaoRepository.save(account);
    }

    public boolean deleteById(long id) {
        if (accountDaoRepository.existsById(id)) {
            accountDaoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Account getOne(long id) {
        return accountDaoRepository.findById(id).orElse(null);
    }

    public List<Account> findAll() {
        return accountDaoRepository.findAll();
    }


    public Account deposit(String number, Double amount) {
        Account account = accountDaoRepository.findByNumber(number);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return accountDaoRepository.save(account);
        }
        return null;
    }

    public Account withdraw(String number, Double amount) {
        Account account = accountDaoRepository.findByNumber(number);
        if (account != null) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return accountDaoRepository.save(account);
            }
        }
        return null;
    }

    public boolean transfer(String fromNumber, String toNumber, Double amount) {
        Account fromAccount = accountDaoRepository.findByNumber(fromNumber);
        Account toAccount = accountDaoRepository.findByNumber(toNumber);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                accountDaoRepository.save(fromAccount);
                accountDaoRepository.save(toAccount);
                return true;
            }
        }
        return false;
    }
}










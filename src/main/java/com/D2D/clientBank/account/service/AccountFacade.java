package com.D2D.clientBank.account.service;

import com.D2D.clientBank.account.api.dto.AccountRequest;
import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.account.db.Account;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountFacade {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = modelMapper.map(accountRequest, Account.class);
        account = accountService.save(account);
        return modelMapper.map(account, AccountResponse.class);
    }

    public AccountResponse getAccount(long id) {
        Account account = accountService.getOne(id);
        return modelMapper.map(account, AccountResponse.class);
    }

    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountService.findAll();
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    public boolean deleteAccount(long id) {
        return accountService.deleteById(id);
    }

    public AccountResponse deposit(String number, Double amount) {
        Account account = accountService.deposit(number, amount);
        return modelMapper.map(account, AccountResponse.class);
    }

    public AccountResponse withdraw(String number, Double amount) {
        Account account = accountService.withdraw(number, amount);
        return modelMapper.map(account, AccountResponse.class);
    }

    public boolean transfer(String fromNumber, String toNumber, Double amount) {
        return accountService.transfer(fromNumber, toNumber, amount);
    }
}



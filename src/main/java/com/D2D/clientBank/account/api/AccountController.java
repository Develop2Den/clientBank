package com.D2D.clientBank.account.api;

import com.D2D.clientBank.account.api.dto.AccountRequest;
import com.D2D.clientBank.account.api.dto.AccountResponse;
import com.D2D.clientBank.account.db.AccountDaoRepository;
import com.D2D.clientBank.account.service.AccountFacade;
import com.D2D.clientBank.webSocket.WebSocketController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountFacade accountFacade;
    private final WebSocketController webSocketController;
    private final AccountDaoRepository accountDaoRepository;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest) {
        log.info("Creating account with request: {}", accountRequest);
        try {
            AccountResponse accountResponse = accountFacade.createAccount(accountRequest);
            log.info("Account created successfully: {}", accountResponse);
            return ResponseEntity.ok(accountResponse);
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create account: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountResponse> deposit(@RequestParam String number, @RequestParam Double amount) {
        log.info("Depositing {} to account number {}", amount, number);
        AccountResponse accountResponse = accountFacade.deposit(number, amount);
        if (accountResponse != null) {
            log.info("Deposit successful for account number {}", number);
            webSocketController.sendAccountUpdate(accountResponse.getCustomerId(), "Account balance updated.");
            return ResponseEntity.ok(accountResponse);
        }
        log.warn("Deposit failed for account number {}", number);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@RequestParam String number, @RequestParam Double amount) {
        log.info("Withdrawing {} from account number {}", amount, number);
        AccountResponse accountResponse = accountFacade.withdraw(number, amount);
        if (accountResponse != null) {
            log.info("Withdrawal successful for account number {}", number);
            webSocketController.sendAccountUpdate(accountResponse.getCustomerId(), "Account balance updated.");
            return ResponseEntity.ok(accountResponse);
        }
        log.warn("Withdrawal failed for account number {}", number);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromNumber, @RequestParam String toNumber, @RequestParam Double amount) {
        log.info("Transferring {} from account number {} to account number {}", amount, fromNumber, toNumber);
        boolean success = accountFacade.transfer(fromNumber, toNumber, amount);
        if (success) {
            log.info("Transfer successful from {} to {}", fromNumber, toNumber);

            Long customerId = accountDaoRepository.findByNumber(fromNumber).getCustomer().getId();
            log.error(customerId.toString());
            String message = "Transfer of " + amount + " from account " + fromNumber + " to account " + toNumber + " was successful.";
            webSocketController.sendAccountUpdate(customerId, message);

            return ResponseEntity.ok("Transfer successful");
        }
        log.warn("Transfer failed from {} to {}", fromNumber, toNumber);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer failed");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id) {
        log.info("Deleting account with ID {}", id);
        boolean deleted = accountFacade.deleteAccount(id);
        if (deleted) {
            log.info("Account with ID {} deleted", id);
            return ResponseEntity.ok("Account deleted");
        }
        log.warn("Account with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable long id) {
        log.info("Retrieving account with ID {}", id);
        AccountResponse accountResponse = accountFacade.getAccount(id);
        if (accountResponse != null) {
            log.info("Account retrieved: {}", accountResponse);
            return ResponseEntity.ok(accountResponse);
        }
        log.warn("Account with ID {} not found", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        log.info("Retrieving all accounts");
        List<AccountResponse> accounts = accountFacade.getAllAccounts();
        log.info("Accounts retrieved: {}", accounts);
        return ResponseEntity.ok(accounts);
    }
}






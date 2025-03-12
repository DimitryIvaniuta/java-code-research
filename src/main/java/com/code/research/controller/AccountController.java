package com.code.research.controller;

import com.code.research.model.Account;
import com.code.research.service.transaction.isolation.AccountService;
import com.code.research.service.transaction.isolation.AccountServiceReadCommitted;
import com.code.research.service.transaction.isolation.AccountServiceReadUncommitted;
import com.code.research.service.transaction.isolation.AccountServiceRepeatableRead;
import com.code.research.service.transaction.isolation.AccountServiceSerializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired private AccountServiceReadUncommitted readUncommittedService;
    @Autowired private AccountServiceReadCommitted readCommittedService;
    @Autowired private AccountServiceRepeatableRead repeatableReadService;
    @Autowired private AccountServiceSerializable serializableService;
    @Autowired private AccountService accountService;  // for updates

    // Endpoint to test READ_UNCOMMITTED isolation
    @GetMapping("/isolation/read-uncommitted/{id}")
    public String getAccountReadUncommitted(@PathVariable("id") Long accountId) {
        return readUncommittedService.readAccountUncommitted(accountId);
    }

    // Endpoint to test READ_COMMITTED isolation
    @GetMapping("/isolation/read-committed/{id}")
    public String getAccountReadCommitted(@PathVariable("id") Long accountId) {
        return readCommittedService.readAccountCommitted(accountId);
    }

    // Endpoint to test REPEATABLE_READ isolation
    @GetMapping("/isolation/repeatable-read/{id}")
    public String getAccountRepeatableRead(@PathVariable("id") Long accountId) {
        return repeatableReadService.reReadAccount(accountId);
    }

    // Endpoint to test SERIALIZABLE isolation
    @GetMapping("/isolation/serializable/{id}")
    public String getAccountSerializable(@PathVariable("id") Long accountId) {
        return serializableService.readAccountSerializable(accountId);
    }

    // Endpoint to update an account's balance (simulate a concurrent update)
    @PutMapping("/{id}/balance")
    public Account updateAccountBalance(@PathVariable("id") Long accountId,
                                        @RequestParam BigDecimal balance) {
        // This will perform the update in its own transaction
        return accountService.updateBalance(accountId, balance);
    }
}

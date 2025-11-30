package com.code.research.service.transaction.isolation;

import com.code.research.model.Account;
import com.code.research.springboot.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceReadUncommitted {

    private final AccountRepository accountRepository;

    public AccountServiceReadUncommitted(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public String readAccountUncommitted(Long accountId) {
        // First read: capture the initial balance.
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return "Account not found";
        }
        Account account = optionalAccount.get();
        BigDecimal initialBalance = account.getBalance();
        log.info("READ_UNCOMMITTED - Initial Balance: {}", initialBalance);

        // Simulate a processing delay.
        try {
            Thread.sleep(5000); // 5-second delay to allow concurrent modifications
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Transaction interrupted";
        }

        // Second read: re-read the account within the same transaction.
        Optional<Account> secondRead = accountRepository.findById(accountId);
        if (secondRead.isEmpty()) {
            return "Account not found on second read";
        }
        BigDecimal finalBalance = secondRead.get().getBalance();
        log.info("READ_UNCOMMITTED - Final Balance: {}", finalBalance);

        return "READ_UNCOMMITTED -> Initial balance: " + initialBalance + ", Final balance: " + finalBalance;
    }

}

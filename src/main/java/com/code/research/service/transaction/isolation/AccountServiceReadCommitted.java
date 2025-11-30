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
public class AccountServiceReadCommitted {

    private final AccountRepository accountRepository;

    public AccountServiceReadCommitted(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String readAccountCommitted(Long accountId) {
        // First read: capture the initial balance.
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return "Account not found";
        }
        Account account = optionalAccount.get();
        BigDecimal initialBalance = account.getBalance();
        log.info("READ_COMMITTED - Initial Balance: " + initialBalance);

        // Simulate processing delay.
        try {
            Thread.sleep(5000); // Delay to allow concurrent updates
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Transaction interrupted";
        }

        // Second read: re-read the account.
        Optional<Account> secondRead = accountRepository.findById(accountId);
        if (secondRead.isEmpty()) {
            return "Account not found on second read";
        }
        BigDecimal finalBalance = secondRead.get().getBalance();
        log.info("READ_COMMITTED - Final Balance: {}", finalBalance);

        return "READ_COMMITTED -> Initial balance: " + initialBalance + ", Final balance: " + finalBalance;
    }

}

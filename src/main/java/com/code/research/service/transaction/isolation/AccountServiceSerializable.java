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
public class AccountServiceSerializable {

    private final AccountRepository accountRepository;

    public AccountServiceSerializable(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String readAccountSerializable(Long accountId) {
        // First read: capture the initial balance.
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return "Account not found";
        }
        Account account = optionalAccount.get();
        BigDecimal initialBalance = account.getBalance();
        log.info("SERIALIZABLE - Initial Balance: {}", initialBalance);

        // Simulate processing delay.
        try {
            Thread.sleep(5000); // Delay to simulate long transaction
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
        log.info("SERIALIZABLE - Final Balance: {}", finalBalance);

        return "SERIALIZABLE -> Initial balance: " + initialBalance + ", Final balance: " + finalBalance;
    }
}

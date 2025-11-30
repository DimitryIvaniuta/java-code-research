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
public class AccountServiceRepeatableRead {

    private final AccountRepository accountRepository;

    public AccountServiceRepeatableRead(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Reads an account, waits for 5 seconds to simulate a long-running transaction,
     * and then re-reads the account within the same transaction.
     *
     * Under REPEATABLE_READ isolation, the second read returns the same data as the first,
     * even if concurrent transactions have updated the data.
     *
     * @param accountId the ID of the account to read.
     * @return a string summarizing the initial and final balance.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String reReadAccount(Long accountId) {
        // First read: Fetch the account and store the initial balance.
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            return "Account not found";
        }
        Account account = optionalAccount.get();
        BigDecimal initialBalance = account.getBalance();
        log.info("REPEATABLE_READ - Initial Balance: {}", initialBalance);

        // Simulate processing delay to allow concurrent transactions to modify data.
        try {
            Thread.sleep(5000);  // 5-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Transaction interrupted";
        }

        // Second read: Re-fetch the account within the same transaction.
        Optional<Account> optionalAccountSecondRead = accountRepository.findById(accountId);
        if (optionalAccountSecondRead.isEmpty()) {
            return "Account not found on second read";
        }
        Account accountSecondRead = optionalAccountSecondRead.get();
        BigDecimal finalBalance = accountSecondRead.getBalance();
        log.info("REPEATABLE_READ - Final Balance: {}", finalBalance);

        // Under Repeatable Read isolation, initialBalance and finalBalance should be the same,
        // even if another transaction modified the account in the meantime.
        return "Repeatable-Read -> Initial balance: " + initialBalance + ", Final balance: " + finalBalance;
    }
}

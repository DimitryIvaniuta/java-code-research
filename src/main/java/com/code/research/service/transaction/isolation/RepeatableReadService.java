package com.code.research.service.transaction.isolation;

import com.code.research.model.Account;
import com.code.research.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RepeatableReadService {

    private final AccountRepository accountRepository;

    public RepeatableReadService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void processAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.setBalance(account.getBalance().add(BigDecimal.valueOf(100)));
            accountRepository.save(account);
        }

        Account reReadAccount = accountRepository.findById(accountId).orElse(null);
    }
}

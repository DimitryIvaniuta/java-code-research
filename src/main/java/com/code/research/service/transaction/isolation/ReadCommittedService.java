package com.code.research.service.transaction.isolation;

import com.code.research.model.Account;
import com.code.research.springboot.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadCommittedService {

    private final AccountRepository accountRepository;

    public ReadCommittedService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Account getAccountData(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }
}
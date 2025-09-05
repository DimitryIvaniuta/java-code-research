package com.code.research.singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private final SecretClock clock;

    public ReportService(SecretClock clock) {
        this.clock = clock;
    }

    public String generate(){
        // Always the same singleton instance
        return "report-ts=" + clock.now() + ", singletonHash=" + System.identityHashCode(clock);
    }
}

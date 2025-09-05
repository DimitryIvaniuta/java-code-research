package com.code.research.singleton;

import org.springframework.stereotype.Component;

//@Component
final class SecretClock {

    private SecretClock() {
    }

    private static class SecretClockHolder {

        private static final SecretClock INSTANCE = new SecretClock();
    }

    static SecretClock getInstance() { return SecretClockHolder.INSTANCE; }

    long now() { return System.currentTimeMillis(); }
}

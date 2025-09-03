package com.code.research.function.tokencache.facade;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe per-user token cache with TTL and refresh-ahead.
 * Internals are hidden behind the Facade below.
 */
final class GeneratedTokenService {
    private final ConcurrentHashMap<String, Token> cache = new ConcurrentHashMap<>();
    private final TokenGenerator generator;
    private final Duration ttl;
    private final Duration refreshAhead;
    private final Clock clock;

    GeneratedTokenService(TokenGenerator generator, Duration ttl, Duration refreshAhead, Clock clock) {
        this.generator    = Objects.requireNonNull(generator, "generator");
        this.ttl          = Objects.requireNonNull(ttl, "ttl");
        this.refreshAhead = Objects.requireNonNull(refreshAhead, "refreshAhead");
        this.clock        = Objects.requireNonNull(clock, "clock");
        if (!refreshAhead.isNegative() && !refreshAhead.isZero() && !refreshAhead.minus(ttl).isNegative()) {
            throw new IllegalArgumentException("refreshAhead must be < ttl");
        }
    }
    GeneratedTokenService(TokenGenerator generator, Duration ttl, Duration refreshAhead) {
        this(generator, ttl, refreshAhead, Clock.systemUTC());
    }

    /** Core: atomic per-key compute; refresh if expiring soon. */
    Token get(User user) {
        Objects.requireNonNull(user, "user");
        String userId = Objects.requireNonNull(user.id(), "user.id");
        return cache.compute(userId, (id, current) -> {
            Instant now = Instant.now(clock);
            if (current == null || isExpiringSoon(current, now)) {
                return generator.generate(user, now, ttl);
            }
            return current;
        });
    }

    void evict(String userId) { cache.remove(Objects.requireNonNull(userId, "userId")); }
    void clear()              { cache.clear(); }

    private boolean isExpiringSoon(Token t, Instant now) {
        return !now.isBefore(t.expiresAt().minus(refreshAhead)); // now >= (exp - refreshAhead)
    }
}
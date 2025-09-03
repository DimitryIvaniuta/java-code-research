package com.code.research.function.tokencache;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Generated-Token cache (per user):
 * - Thread-safe cache keyed by userId.
 * - compute(...) ensures exactly one generator invocation per key at a time.
 * - Refresh-ahead prevents returning nearly expired tokens.
 */
final class GeneratedTokenService {

    private final ConcurrentHashMap<String, Token> cache = new ConcurrentHashMap<>();
    private final TokenGenerator generator;
    private final Duration ttl;
    private final Duration refreshAhead;
    private final Clock clock;

    /**
     * @param generator    strategy used to mint tokens
     * @param ttl          token lifetime (e.g., PT5M)
     * @param refreshAhead if token expires within this window, regenerate (e.g., PT30S)
     * @param clock        injectable clock for tests
     */
    GeneratedTokenService(TokenGenerator generator, Duration ttl, Duration refreshAhead, Clock clock) {
        this.generator = Objects.requireNonNull(generator, "generator");
        this.ttl = Objects.requireNonNull(ttl, "ttl");
        this.refreshAhead = Objects.requireNonNull(refreshAhead, "refreshAhead");
        this.clock = Objects.requireNonNull(clock, "clock");
        if (!refreshAhead.isNegative() && !refreshAhead.isZero() && refreshAhead.compareTo(ttl) >= 0) {
            throw new IllegalArgumentException("refreshAhead must be < ttl");
        }
    }

    /** Convenience ctor with system clock. */
    GeneratedTokenService(TokenGenerator generator, Duration ttl, Duration refreshAhead) {
        this(generator, ttl, refreshAhead, Clock.systemUTC());
    }

    /** The requested "Function<User, Token>" provider. */
    Function<User, Token> tokenFunction() {
        return this::getToken;
    }

    /** Get (or generate/refresh) a token for the given user. */
    Token getToken(User user) {
        Objects.requireNonNull(user, "user");
        String userId = Objects.requireNonNull(user.id(), "user.id");
        // compute(...) is atomic per key: one thread runs the lambda; others for same key wait.
        return cache.compute(userId, (id, current) -> {
            Instant now = Instant.now(clock);

            // If nothing cached OR expires within refreshAhead window → (re)generate.
            if (current == null || isExpiringSoon(current, now)) {
                return generator.generate(user, now, ttl);
            }

            // Still healthy → reuse cached token (fast path).
            return current;
        });
    }

    /** Remove token for a specific user (e.g., logout). */
    void evict(String userId) {
        cache.remove(Objects.requireNonNull(userId, "userId"));
    }

    /** Clear all tokens (e.g., key rotation). */
    void clear() {
        cache.clear();
    }

    private boolean isExpiringSoon(Token t, Instant now) {
        Instant refreshPoint = t.expiresAt().minus(refreshAhead);
        return !now.isBefore(refreshPoint); // now >= (exp - refreshAhead)
    }
}
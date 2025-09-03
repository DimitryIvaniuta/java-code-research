package com.code.research.function.tokencache;

import java.util.Objects;
import java.util.function.Function;

/**
 * Provides per-user tokens from a cached token function.
 * Encapsulates the low-level {@code tokenFn.apply(user)} call behind a readable API.
 *
 * Usage intent: "give me the cached token for this user, or generate a new one if needed".
 */
public final class CachedUserTokenProvider {

    private final Function<User, Token> tokenFunction;

    /**
     * @param tokenFunction a function that returns a cached token for a user,
     *                      generating a new one when absent/expiring
     */
    public CachedUserTokenProvider(Function<User, Token> tokenFunction) {
        this.tokenFunction = Objects.requireNonNull(tokenFunction, "tokenFunction");
    }

    /** Convenience factory from the previously defined GeneratedTokenService. */
    public static CachedUserTokenProvider from(GeneratedTokenService service) {
        Objects.requireNonNull(service, "service");
        return new CachedUserTokenProvider(service.tokenFunction());
    }

    /**
     * Returns a cached token for the given user, generating/refreshing if necessary.
     * (Internally delegates to the memoized/TTL-aware function.)
     */
    public Token getOrGenerateToken(User user) {
        Objects.requireNonNull(user, "user");
        return tokenFunction.apply(user);
    }

    /** Alias with a very concise name, if you prefer. */
    public Token tokenFor(User user) { return getOrGenerateToken(user); }
}

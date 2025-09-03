package com.code.research.function.tokencache.facade;

import java.net.http.HttpRequest;
import java.util.Map;
import java.util.Objects;

/**
 * FAÇADE: single entry to obtain/apply tokens.
 * Hides all caching/refresh details.
 */
public final class AccessTokenFacade {

    private final GeneratedTokenService tokenService;

    public AccessTokenFacade(GeneratedTokenService tokenService) {
        this.tokenService = Objects.requireNonNull(tokenService, "tokenService");
    }

    /** Primary API: get a valid (cached or freshly minted) token for a user. */
    public Token tokenFor(User user) {
        return tokenService.get(user);
    }

    /** Header pair for easy use in arbitrary HTTP clients. */
    public Map.Entry<String,String> authorizationHeader(User user) {
        String bearer = "Bearer " + tokenFor(user).value();
        return Map.entry("Authorization", bearer);
    }

    /** Mutate a builder to include Authorization. */
    public HttpRequest.Builder authorize(User user, HttpRequest.Builder builder) {
        var h = authorizationHeader(user);
        return builder.header(h.getKey(), h.getValue());
    }

    /** Utilities for security ops. */
    public void evict(String userId) { tokenService.evict(userId); }
    public void clear()              { tokenService.clear(); }
}

package com.code.research.function.tokencache;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CachedUserTokenProviderApp {

    public static void main(String[] args) {
        // Build your token generator + cache service (from previous example)
        var generator = new HmacJwtLikeGenerator("super-secret-key-123".getBytes());
        var service   = new GeneratedTokenService(
                generator,
                java.time.Duration.ofSeconds(5),
                java.time.Duration.ofSeconds(1)
        );

// Wrap it
        var tokenProvider = CachedUserTokenProvider.from(service);

// Use it anywhere in your codebase:
        User user = new User("U-42", "dev@example.com", java.util.Set.of("USER","ADMIN"));
        Token token = tokenProvider.getOrGenerateToken(user);
        log.info("Token: {}", token.toString());
// or
        Token token2 = tokenProvider.tokenFor(user);

        log.info("Token2: {}", token2.toString());
    }
}

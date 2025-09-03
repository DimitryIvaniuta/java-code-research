package com.code.research.function.tokencache;

import java.time.Duration;
import java.time.Instant;

/** Strategy: how to generate a token for a given User, "now", and TTL. */
@FunctionalInterface
interface TokenGenerator {
    Token generate(User user, Instant now, Duration ttl);
}
package com.code.research.function.tokencache.facade;

import java.time.Duration;
import java.time.Instant;

/** Strategy for minting tokens. */
@FunctionalInterface
interface TokenGenerator {
    Token generate(User user, Instant now, Duration ttl);
}
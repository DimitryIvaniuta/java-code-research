package com.code.research.function.tokencache.facade;

import java.time.Instant;

public record Token(String value, Instant issuedAt, Instant expiresAt) {}
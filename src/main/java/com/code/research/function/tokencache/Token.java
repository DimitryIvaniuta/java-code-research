package com.code.research.function.tokencache;

import java.time.Instant;

/** Token value with timing info (record gives equals/hashCode/toString). */
record Token(String value, Instant issuedAt, Instant expiresAt) {}
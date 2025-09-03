package com.code.research.function.tokencache.facade;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tiny HMAC-SHA256 token/JWT-like generator.
 * Demo only — use a real JWT/IdP in production.
 */
final class HmacJwtLikeGenerator implements TokenGenerator {
    private static final Base64.Encoder B64 = Base64.getUrlEncoder().withoutPadding();
    private final SecretKeySpec keySpec;
    private final AtomicInteger mints = new AtomicInteger();

    HmacJwtLikeGenerator(byte[] secret) {
        this.keySpec = new SecretKeySpec(Objects.requireNonNull(secret, "secret"), "HmacSHA256");
    }

    @Override public Token generate(User user, Instant now, Duration ttl) {
        mints.incrementAndGet();
        long iat = now.getEpochSecond();
        long exp = now.plus(ttl).getEpochSecond();

        String header  = B64.encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        String payload = B64.encodeToString((
                "{\"sub\":\"" + esc(user.id()) + "\",\"iat\":" + iat + ",\"exp\":" + exp + "}"
        ).getBytes(StandardCharsets.UTF_8));

        String signingInput = header + "." + payload;
        String signature    = sign(signingInput);
        return new Token(signingInput + "." + signature, now, Instant.ofEpochSecond(exp));
    }

    int mintedCount() { return mints.get(); }

    private String sign(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            return B64.encodeToString(mac.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) { throw new RuntimeException("HMAC failure", e); }
    }
    private static String esc(String s){ return s.replace("\\","\\\\").replace("\"","\\\""); }
}

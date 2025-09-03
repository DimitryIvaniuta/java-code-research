package com.code.research.function.tokencache;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HMAC-SHA256 "toy JWT" generator (header.payload.signature).
 * For demo purposes only — in production, use a proper JWT library / IdP.
 */
final class HmacJwtLikeGenerator implements TokenGenerator {
    private static final Base64.Encoder B64 = Base64.getUrlEncoder().withoutPadding();
    private final SecretKeySpec keySpec;
    private final AtomicInteger generates = new AtomicInteger(); // observe how many times we actually mint

    HmacJwtLikeGenerator(byte[] secret) {
        this.keySpec = new SecretKeySpec(Objects.requireNonNull(secret, "secret"), "HmacSHA256");
    }

    @Override
    public Token generate(User user, Instant now, Duration ttl) {
        generates.incrementAndGet(); // for demo metrics

        long iat = now.getEpochSecond();
        long exp = now.plus(ttl).getEpochSecond();

        String headerJson  = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"sub\":\"" + escape(user.id()) + "\",\"iat\":" + iat + ",\"exp\":" + exp + "}";

        String header  = B64.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = B64.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String signingInput = header + "." + payload;

        String signature = sign(signingInput);
        String tokenValue = signingInput + "." + signature;

        return new Token(tokenValue, now, Instant.ofEpochSecond(exp));
    }

    int mintedCount() { return generates.get(); }

    private String sign(String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(keySpec);
            byte[] sig = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return B64.encodeToString(sig);
        } catch (Exception e) {
            throw new RuntimeException("HMAC failure", e);
        }
    }

    // Minimal JSON string escaping for demo (IDs are simple here).
    private static String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

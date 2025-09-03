package com.code.research.function.tokencache.facade;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * DECORATOR over Java HttpClient that injects Authorization automatically.
 */
final class AuthorizedHttpClient {
    private final HttpClient delegate;
    private final AccessTokenFacade tokens;

    public AuthorizedHttpClient(HttpClient delegate, AccessTokenFacade tokens) {
        this.delegate = Objects.requireNonNull(delegate, "delegate");
        this.tokens   = Objects.requireNonNull(tokens, "tokens");
    }

    /** Synchronous send with automatic Authorization header. */
    public <T> HttpResponse<T> send(User user, HttpRequest request, HttpResponse.BodyHandler<T> handler)
            throws java.io.IOException, InterruptedException {
        HttpRequest authorized = withAuthorization(user, request);
        return delegate.send(authorized, handler);
    }

    /** Async send with automatic Authorization header. */
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(
            User user, HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        HttpRequest authorized = withAuthorization(user, request);
        return delegate.sendAsync(authorized, handler);
    }

    /* Build a new request copying all fields but injecting Authorization. */
    private HttpRequest withAuthorization(User user, HttpRequest original) {
        var auth = tokens.authorizationHeader(user);

        HttpRequest.Builder b = HttpRequest.newBuilder(original.uri());
        // copy method + body
        HttpRequest.BodyPublisher body = original.bodyPublisher().orElse(HttpRequest.BodyPublishers.noBody());
        b.method(original.method(), body);
        // copy headers except existing Authorization
        original.headers().map().forEach((k, vals) -> {
            if (!k.equalsIgnoreCase("Authorization")) vals.forEach(v -> b.header(k, v));
        });
        // add our Authorization
        b.header(auth.getKey(), auth.getValue());
        // copy version/timeout/expect-continue if present
        original.version().ifPresent(b::version);
        original.timeout().ifPresent(b::timeout);
        if (original.expectContinue()) b.expectContinue(true);
        return b.build();
    }
}

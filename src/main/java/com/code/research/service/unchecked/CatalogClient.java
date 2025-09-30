package com.code.research.service.unchecked;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

record Result<T>(T value, Exception error) {
    static <T> Result<T> ok(T value) {
        return new Result<T>(value, null);
    }
    static <T> Result<T> err(Exception error) {
        return new Result<T>(null, error);
    }
    boolean isOk() { return error == null; }
}

class CatalogClient {
    private final HttpClient httpClient;

    public CatalogClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    public List<String> fetchTitles(List<String> ids){
        List<Result<String>> results = ids.stream()
                .map(id->safeFetch("https://catalog.com/item/"+id))
                .toList();
        results.stream().filter(i->!i.isOk()).forEach(r -> r.error().printStackTrace());
        return results.stream().filter(Result::isOk).map(r -> r.value()).toList();
    }

    private Result<String> safeFetch(String url) {
        try{
            var httpRequest = HttpRequest.newBuilder(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(3))
                    .build();
            var body = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
            return Result.ok(body);
        } catch (Exception e) {
            if (e instanceof InterruptedException ie) { Thread.currentThread().interrupt(); }
            return Result.err(e);
        }
    }
}

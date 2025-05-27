package com.code.research.concurrent.email;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

public final class EmailService {
    private static final String API_KEY    = System.getenv("MAILGUN_API_KEY");
    private static final String DOMAIN     = System.getenv("MAILGUN_DOMAIN");
    private static final String BASE_URL   = "https://api.mailgun.net/v3/" + DOMAIN + "/messages";
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private EmailService() { }

    public static void send(String to, String subject, String textBody) {
        // 1) Prepare Basic auth header
        String auth = "Basic " + Base64.getEncoder()
                .encodeToString(("api:" + API_KEY)
                        .getBytes(StandardCharsets.UTF_8));

        // 2) Build form parameters
        Map<String,String> form = Map.of(
                "from",    "no-reply@" + DOMAIN,
                "to",      to,
                "subject", subject,
                "text",    textBody
        );
        String formData = form.entrySet().stream()
                .map(e -> URLEncoder.encode(e.getKey(),   StandardCharsets.UTF_8)
                        + "=" +
                        URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        // 3) Build and send the HTTP request
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .timeout(Duration.ofSeconds(5))
                .header("Authorization", auth)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData))
                .build();

        try {
            HttpResponse<String> resp = CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
            int status = resp.statusCode();
            if (status >= 200 && status < 300) {
                System.out.println("Email sent to " + to);
            } else {
                throw new RuntimeException(
                        "Failed to send email: HTTP " + status + " — " + resp.body());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error sending email", e);
        }
    }
}

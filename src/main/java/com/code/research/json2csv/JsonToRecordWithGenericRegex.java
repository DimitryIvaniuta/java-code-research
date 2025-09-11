package com.code.research.json2csv;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class JsonToRecordWithGenericRegex {

    // 1) Define record matching the JSON structure
    public record Contact(String name, String email, String phone) {
    }

    public static void main(String[] args) {
        String json = """
                {
                  "name": "Alice Smith",
                  "email": "alice.smith@example.com",
                  "phone": "+48-123-456-789"
                }
                """;

        // 2) Generic pattern: capture any "key": "value" pairs
        Pattern pair = Pattern.compile("\"(\\w+)\"\\s*:\\s*\"([^\"]*)\"");
        Matcher m = pair.matcher(json);

        // 3) Collect into map
        Map<String, String> fields = new HashMap<>();
        while (m.find()) {
            fields.put(m.group(1), m.group(2));
        }

        // 4) Instantiate record using map lookups
        Contact contact = new Contact(
                fields.get("name"),
                fields.get("email"),
                fields.get("phone")
        );

        // 5) Use the mapped object
        log.info("Name : {}", contact.name());
        log.info("Email: {}", contact.email());
        log.info("Phone: {}", contact.phone());
    }
}

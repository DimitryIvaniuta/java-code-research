package com.code.research.customvalidator;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserCodeController {

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateUserRequest request) {
        // if country invalid -> 400 with validation error
        return ResponseEntity.ok().build();
    }
}

package com.shariq.landing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

/**
 * Handles the landing page's contact form. Each submission is written to the
 * application log as one JSON line tagged CONTACT_SUBMISSION, so searching the
 * logs for that tag returns whole messages. Serialising through Jackson escapes
 * newlines and quotes, which keeps a multi-line message on a single log line and
 * stops crafted input from forging log entries.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ObjectMapper objectMapper;

    public ContactController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "time", Instant.now().toString()
        ));
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> submitContact(@Valid @RequestBody ContactRequest request) {
        LoggedSubmission entry = new LoggedSubmission(
                Instant.now().toString(), request.name(), request.email(), request.message());
        log.info("CONTACT_SUBMISSION {}", objectMapper.valueToTree(entry));

        return ResponseEntity.ok(Map.of(
                "status", "received",
                "message", "Thanks! We'll get back to you soon."
        ));
    }

    public record ContactRequest(
            @NotBlank @Size(max = 100) String name,
            @NotBlank @Email @Size(max = 150) String email,
            @NotBlank @Size(max = 2000) String message
    ) {}

    public record LoggedSubmission(String receivedAt, String name, String email, String message) {}
}

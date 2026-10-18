package com.shariq.landing.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles the landing page's contact / signup form.
 * Submissions are kept in memory for now (fine for a first launch) -
 * swap the storage layer for a database later without changing the frontend contract.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final List<ContactSubmission> submissions = new CopyOnWriteArrayList<>();

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "time", Instant.now().toString()
        ));
    }

    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> submitContact(@Valid @RequestBody ContactRequest request) {
        ContactSubmission submission = new ContactSubmission(
                request.name(), request.email(), request.message(), Instant.now());
        submissions.add(submission);
        log.info("New contact submission from {} <{}>", submission.name(), submission.email());

        return ResponseEntity.ok(Map.of(
                "status", "received",
                "message", "Thanks! We'll get back to you soon."
        ));
    }

    // --- internal types ---

    public record ContactRequest(
            @NotBlank @Size(max = 100) String name,
            @NotBlank @Email @Size(max = 150) String email,
            @NotBlank @Size(max = 2000) String message
    ) {}

    private record ContactSubmission(String name, String email, String message, Instant receivedAt) {}
}

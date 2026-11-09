package com.shariq.shariqtech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariq.shariqtech.model.ContactSubmission;
import com.shariq.shariqtech.repository.ContactSubmissionRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

/**
 * Handles the landing page's contact form. Submissions are stored in Postgres
 * and also written to the log as one JSON line tagged CONTACT_SUBMISSION.
 * Serialising through Jackson escapes newlines and quotes, which keeps a
 * multi-line message on a single log line and stops crafted input from forging
 * log entries.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ObjectMapper objectMapper;
    private final ContactSubmissionRepository repository;

    public ContactController(ObjectMapper objectMapper, ContactSubmissionRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
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
        Instant receivedAt = Instant.now();

        // Logged before the insert, so the message survives a database outage.
        log.info("CONTACT_SUBMISSION {}", objectMapper.valueToTree(new LoggedSubmission(
                receivedAt.toString(), request.name(), request.email(), request.message())));

        try {
            repository.save(new ContactSubmission(
                    request.name(), request.email(), request.message(), receivedAt));
        } catch (DataAccessException | TransactionException e) {
            // TransactionException covers an unreachable database, which surfaces as
            // CannotCreateTransactionException rather than a DataAccessException.
            // The log line above is the fallback record, so the sender is still told
            // the message went through rather than being asked to resend it.
            log.error("Could not persist contact submission from {}", request.email(), e);
        }

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

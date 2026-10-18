package com.shariq.landing.controller;

import com.shariq.landing.model.ContactSubmission;
import com.shariq.landing.repository.ContactSubmissionRepository;
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
 * Handles the landing page's contact form. Submissions are persisted so they
 * survive restarts and redeploys; read them back via the secured /admin page.
 */
@RestController
@RequestMapping("/api")
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactSubmissionRepository repository;

    public ContactController(ContactSubmissionRepository repository) {
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
        ContactSubmission saved = repository.save(new ContactSubmission(
                request.name(), request.email(), request.message(), Instant.now()));
        log.info("New contact submission #{} from {}", saved.getId(), saved.getEmail());

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
}
